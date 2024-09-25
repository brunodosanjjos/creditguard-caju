package br.com.caju.transacionmanager.domain.exception;

public class InsufficientFundsException extends Throwable {
    public InsufficientFundsException(String message) {
    super(message);
    }
}
