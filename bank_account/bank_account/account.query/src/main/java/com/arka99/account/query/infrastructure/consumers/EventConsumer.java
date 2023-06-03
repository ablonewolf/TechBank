package com.arka99.account.query.infrastructure.consumers;

import com.arka99.account.common.events.AccountClosedEvent;
import com.arka99.account.common.events.AccountOpenedEvent;
import com.arka99.account.common.events.FundsDepositedEvent;
import com.arka99.account.common.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent openedEvent, Acknowledgment acknowledgment);

    void consume(@Payload FundsDepositedEvent depositedEvent, Acknowledgment acknowledgment);

    void consume(@Payload FundsWithdrawnEvent withdrawnEvent, Acknowledgment acknowledgment);

    void consume(@Payload AccountClosedEvent closedEvent, Acknowledgment acknowledgment);
}
