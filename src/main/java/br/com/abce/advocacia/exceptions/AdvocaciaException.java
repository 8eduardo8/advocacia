package br.com.abce.advocacia.exceptions;

public class AdvocaciaException extends Exception {

    public AdvocaciaException() {
        super();
    }

    public AdvocaciaException(String message) {
        super(message);
    }

    public AdvocaciaException(String message, Throwable cause) {
        super(message, cause);
    }
}
