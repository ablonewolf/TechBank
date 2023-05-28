package com.arka99.cqrs.core.handlers;

import com.arka99.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);

    T getById(String id);
}
