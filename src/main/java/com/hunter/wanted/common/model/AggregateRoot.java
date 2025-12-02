package com.hunter.wanted.common.model;

import com.hunter.wanted.common.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public class AggregateRoot {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected void registerEvent(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> copied = List.copyOf(domainEvents);
        domainEvents.clear();
        return copied;
    }

}
