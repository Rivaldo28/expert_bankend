package br.com.rivaldo.models.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class StandardError {
    private LocalDateTime timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
