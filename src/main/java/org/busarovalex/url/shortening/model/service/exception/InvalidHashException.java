package org.busarovalex.url.shortening.model.service.exception;

public class InvalidHashException extends BusinessException {
    public InvalidHashException(String hash) {
        super(hash);
    }

    public InvalidHashException() {
    }
}
