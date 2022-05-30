package org.busarovalex.url.shortening.controller;

import org.busarovalex.url.shortening.dto.ErrorDto;
import org.busarovalex.url.shortening.model.service.exception.BusinessException;
import org.busarovalex.url.shortening.model.service.exception.HashNotFoundException;
import org.busarovalex.url.shortening.model.service.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler({ BusinessException.class })
    public ResponseEntity<ErrorDto> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ValidationException.class })
    public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage(), ex.getField()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ HashNotFoundException.class })
    public ResponseEntity<ErrorDto> handleHashNotFoundException(HashNotFoundException ex) {
        return new ResponseEntity<>(new ErrorDto("not_found", "hash"), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorDto> handleInternalServerError(Exception ex) {
        LOG.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorDto("internal_server_error"), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
