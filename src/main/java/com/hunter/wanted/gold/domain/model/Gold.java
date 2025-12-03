package com.hunter.wanted.gold.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable // 중요 : 다른 엔티티 (Pocket , payment)의 일부가 될 수 있다는 표시
@Getter
@EqualsAndHashCode // 값 객체VO는 값이 같으면 같은 객체로 취급해야함
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 스펙상 기본 생성자 필요
public class Gold {

     private long amount; // 돈은 int 범위를 넘을 수 있으니 long 추천

    // 생성자 : 검증 로직 포함
    public Gold(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("골드는 0보다 작을 수 없습니다.");
        }
        this.amount = amount;
    }

        // 편의 메서드 (static factory method)
        public static Gold of(long amount) {
            return new Gold(amount);
        }

        // 더하기 로직
    public Gold plus(Gold other) {
        return new Gold(this.amount + other.amount); // 새로운 객체를 반환 (불변성)
    }

    // 빼기 로직 (검증 포함)
    public Gold minus(Gold other) {
        if (this.amount < other.amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        return new Gold(this.amount - other.amount);
    }

    // 비교 로직 (A가 B보다 크거나 같은가?)
    public boolean isGreaterThanEqual(Gold other) {
        return this.amount >= other.amount;
    }
}

/*
* 건물을 지을 때 벽돌(gold)을 만들어야 벽(entity)를 쌓을 수 있음
* payment 나 pocket 을 만들 때 int amount 라ㅣ고 썻다가 나중에 고치는 것보다
* Gold 라는 부품을 먼저 완벽하게 만들어주고 가져다 쓰는게 훨씬 효율적  VO
* */