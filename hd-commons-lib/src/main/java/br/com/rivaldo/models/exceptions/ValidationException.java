package br.com.rivaldo.models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
public class ValidationException extends StandardError{

    @Getter
    private List<FieldError> erros;

    @Getter
    @AllArgsConstructor
    private class  FieldError {
        private String fieldName;
        private String message;
    }

    public void addError(String fieldName, String message) {
        this.erros.add(new FieldError(fieldName, message));
    }
}
