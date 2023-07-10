package br.com.sylviomartins.spring.kafka.demo.configuration;

import br.com.sylviomartins.spring.kafka.demo.domain.vo.EventoVO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class ConsumerConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @Value("${spring.kafka.retry.interval}")
    private Long interval;

    @Value("${spring.kafka.retry.max-attempts}")
    private Long maxAttempts;

    private final KafkaProperties kafkaProperties;

    // Ref.: https://medium.com/lydtech-consulting/kafka-json-serialization-923e3db58662
    private ConsumerFactory<Object, Object> consumerFactory() {
        Map<String, Object> properties = this.kafkaProperties.buildConsumerProperties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        properties.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);
        properties.put(JsonDeserializer.KEY_DEFAULT_TYPE, String.class.getCanonicalName());

        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        properties.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, EventoVO.class.getCanonicalName());

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    private CommonErrorHandler errorHandler() {
        FixedBackOff backOff = new FixedBackOff(this.interval, this.maxAttempts);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler((record, exception) -> {

            System.out.println("Retry attempts exhausted: " + exception.getMessage());

        }, backOff);

        errorHandler.addNotRetryableExceptions(NullPointerException.class);
        errorHandler.addRetryableExceptions(IllegalArgumentException.class);

        return errorHandler;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler());

        // Envia uma confirmacao para cada mensagem processada
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);

        // Verifica o tipo do manipulador de erro
        factory.afterPropertiesSet();

        return factory;
    }

}
