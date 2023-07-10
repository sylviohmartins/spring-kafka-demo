package br.com.sylviomartins.spring.kafka.demo.exception;

public class NullMessageException extends BusinessException {
    private static final long serialVersionUID = 1L;

    public NullMessageException(final String message) {
        super(message);
    }

}
