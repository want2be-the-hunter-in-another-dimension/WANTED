package com.hunter.wanted.global.quest.domain.exception;

import com.hunter.wanted.global.exception.ApplicationException;

public class QuestException extends ApplicationException {

    public QuestException(QuestErrorCode errorCode) {
        super(errorCode);
    }
}
