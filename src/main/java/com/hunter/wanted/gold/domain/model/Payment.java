package com.hunter.wanted.gold.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long hunterId;  // 결제한 사람

    @Embedded // Gold 부품 재사용
    @AttributeOverride(name = "amount", column = @Column(name = "payment_amount"))
    private Gold amount; // 충전하려는 금액

    @Enumerated(EnumType.STRING) // Enum을 DB에 문자로 저장 "SUCCESS"
    @Column(nullable = false)
    private PaymentStatus status;

    //생성자 :  처음엔 무조건 "대기" 상태
    public Payment(Long hunterId, Gold amount) {
        this.hunterId = hunterId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    // -- 비즈니스 로직 (상태 변경) ---

    // 결제 성공 처리
    public void success() {
        this.status = PaymentStatus.SUCCESS;
    }
    public void cancel() {
        this.status = PaymentStatus.FAILED;
    }
}
