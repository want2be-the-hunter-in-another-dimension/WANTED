package com.hunter.wanted.gold.domain.model;

public enum PaymentStatus {
    PENDING, // 대기 (결제 요청 중)
    SUCCESS, // 성공( 돈 지급 완료)
    FAILED, // 실패 (잔액 부족, 오류 등)
    CANCELLED // 취소 (환불)
}
