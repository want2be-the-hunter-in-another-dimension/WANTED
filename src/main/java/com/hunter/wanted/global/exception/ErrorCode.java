package com.hunter.wanted.global.exception;

public interface ErrorCode {
    String code();     // "MEMBER-001"
    String message();  // "회원이 존재하지 않습니다."
    int status();      // HTTP Status
}
