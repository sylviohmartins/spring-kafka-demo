package br.com.sylviomartins.spring.kafka.demo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventTypeConstants {

    public static final String INCLUSAO = "INCLUSAO";

    public static final String AUTORIZACAO = "AUTORIZACAO";

    public static final String EFETIVACAO = "'EFETIVACAO'";

}
