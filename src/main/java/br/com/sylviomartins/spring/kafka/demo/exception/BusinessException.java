package br.com.sylviomartins.spring.kafka.demo.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    public BusinessException(final String message) {
        super(message);
    }

}
