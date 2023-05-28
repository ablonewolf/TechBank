package com.arka99.account.cmd.infrastructure;

import com.arka99.cqrs.core.events.BaseEvent;
import com.arka99.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topicName, BaseEvent event) {
        this.kafkaTemplate.send(topicName, event);
    }
}
