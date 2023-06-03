package com.arka99.account.query.infrastructure.consumers;

import com.arka99.account.common.events.AccountClosedEvent;
import com.arka99.account.common.events.AccountOpenedEvent;
import com.arka99.account.common.events.FundsDepositedEvent;
import com.arka99.account.common.events.FundsWithdrawnEvent;
import com.arka99.account.query.infrastructure.handlers.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer {

    @Autowired
    private EventHandler eventHandler;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountOpenedEvent openedEvent, Acknowledgment acknowledgment) {
        eventHandler.handle(openedEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundsDepositedEvent depositedEvent, Acknowledgment acknowledgment) {
        eventHandler.handle(depositedEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundsWithdrawnEvent withdrawnEvent, Acknowledgment acknowledgment) {
        eventHandler.handle(withdrawnEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountClosedEvent closedEvent, Acknowledgment acknowledgment) {
        eventHandler.handle(closedEvent);
        acknowledgment.acknowledge();
    }
}
