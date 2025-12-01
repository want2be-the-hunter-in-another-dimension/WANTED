package com.hunter.wanted.gold.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pocket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) ///  헌터 ID는 필수
    private Long hunterId;

    @Embedded

    private Gold balance; // 현재 잔액

    // 생성자 초기 잔액 0
    public Pocket(Long hunterId) {
        this.hunterId = hunterId;
        this.balance = Gold.of(0); //
    }



}
