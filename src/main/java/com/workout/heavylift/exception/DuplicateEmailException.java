package com.workout.heavylift.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("이메일이 이미 사용중 입니다. 다른 이메일을 적어주십시오. + " + email);
    }
}
