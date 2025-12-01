package com.hunter.wanted.global.quest.domain.exception;

import com.hunter.wanted.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum QuestErrorCode implements ErrorCode {

    QUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "QUEST-001", "퀘스트를 찾을 수 없습니다."),
    INVALID_QUEST_STATE(HttpStatus.BAD_REQUEST, "QUEST-002", "유효하지 않은 퀘스트 상태입니다."),
    QUEST_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "QUEST-003", "퀘스트 작성자가 아닙니다."),
    DUPLICATE_QUEST_TITLE(HttpStatus.BAD_REQUEST, "QUEST-004", "이미 존재하는 퀘스트 제목입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    QuestErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
