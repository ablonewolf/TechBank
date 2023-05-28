package com.arka99.cqrs.core.producers;

import com.arka99.cqrs.core.events.BaseEvent;

public interface EventProducer {
    public void produce(String topicName, BaseEvent event);
}
