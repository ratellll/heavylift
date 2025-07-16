package com.workout.heavylift.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;
    private final List<String> errors;
}
