package br.com.sylviomartins.spring.kafka.demo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageFormatConstants {

    public static final String EVENT_PROCESSING = "PROCESSANDO: {0} and {1}";

    public static final String CONVERTED_TO_DOMAIN = "CONVERTED: {0}";

    public static final String SUCCESS = "SUCESSO: {0} and {1}";

    public static final String UNKNOWN_ERROR = "ERRO: {0} and {1}";


}
