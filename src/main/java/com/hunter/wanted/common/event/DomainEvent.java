package com.hunter.wanted.common.event;

import java.time.Instant;

public interface DomainEvent {

    String getEventId();

    Instant getOccurredAt();

    default String getType() {
        return getClass().getSimpleName();
    }

}
