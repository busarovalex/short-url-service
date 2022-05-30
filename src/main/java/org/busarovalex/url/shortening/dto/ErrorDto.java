package org.busarovalex.url.shortening.dto;

import java.util.Objects;

public class ErrorDto {
    private String message;
    private String field;

    public ErrorDto() {
    }

    public ErrorDto(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public ErrorDto(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDto errorDto = (ErrorDto) o;
        return Objects.equals(message, errorDto.message) && Objects.equals(field, errorDto.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, field);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
