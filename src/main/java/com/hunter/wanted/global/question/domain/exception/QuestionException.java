package com.hunter.wanted.global.question.domain.exception;

import com.hunter.wanted.global.exception.ApplicationException;
import com.hunter.wanted.global.exception.ErrorCode;

public class QuestionException extends ApplicationException {

    public QuestionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
