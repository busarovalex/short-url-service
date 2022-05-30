package org.busarovalex.url.shortening.model.service.exception;

public class ValidationException extends BusinessException {
    private final String field;

    public ValidationException(String field, String reason) {
        super(reason);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
