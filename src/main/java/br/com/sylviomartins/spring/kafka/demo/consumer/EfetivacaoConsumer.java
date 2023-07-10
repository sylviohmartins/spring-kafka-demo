package br.com.sylviomartins.spring.kafka.demo.consumer;

import br.com.sylviomartins.spring.kafka.demo.domain.document.Boleto;
import br.com.sylviomartins.spring.kafka.demo.domain.mapper.BoletoMapper;
import br.com.sylviomartins.spring.kafka.demo.domain.vo.EventoVO;
import br.com.sylviomartins.spring.kafka.demo.exception.NullMessageException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static br.com.sylviomartins.spring.kafka.demo.constant.EventTypeConstants.EFETIVACAO;
import static br.com.sylviomartins.spring.kafka.demo.constant.MessageFormatConstants.*;
import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class EfetivacaoConsumer {

    protected static final Logger LOGGER = LoggerFactory.getLogger(EfetivacaoConsumer.class);

    private final BoletoMapper boletoMapper;

    @KafkaListener( //
            containerFactory = "kafkaListenerContainerFactory", //
            topics = "efetivacao" //
    )
    public void consume(@Payload final EventoVO message) throws Exception {
        LOGGER.info(format(EVENT_PROCESSING, EFETIVACAO, message));

        if (isNull(message)) {
            throw new NullMessageException("A mensagem é nula.");
        }

        try {
            final Boleto boleto = boletoMapper.toDocument(message);
            LOGGER.info(format(CONVERTED_TO_DOMAIN, boleto));

            // Process

            if (true) {
                throw new IllegalArgumentException("Retry test");
            }

            LOGGER.info(format(SUCCESS, EFETIVACAO, boleto));

        } catch (final Exception unknownException) {
            LOGGER.error(format(UNKNOWN_ERROR, EFETIVACAO, message), unknownException.getMessage());

            throw unknownException;
        }
    }

}
