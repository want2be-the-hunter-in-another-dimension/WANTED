package com.hunter.wanted.global.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

}
