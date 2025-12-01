package com.hunter.wanted.global.question.domain.exception;

import com.hunter.wanted.global.exception.ErrorCode;

public enum QuestionErrorCode implements ErrorCode {

    QUESTION_NOT_FOUND("QUESTION-001", "질문을 찾을 수 없습니다.", 404),
    INVALID_QUESTION_STATE("QUESTION-002", "유효하지 않은 질문 상태입니다.", 400),
    QUESTION_OWNER_MISMATCH("QUESTION-003", "질문 작성자가 아닙니다.", 403),
    DUPLICATE_QUESTION_TITLE("QUESTION-004", "이미 존재하는 질문 제목입니다.", 400);

    private final String code;
    private final String message;
    private final int status;

    QuestionErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public int status() {
        return status;
    }
}
