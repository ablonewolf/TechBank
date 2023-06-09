package com.arka99.account.query.infrastructure.handlers;

import com.arka99.account.common.events.AccountClosedEvent;
import com.arka99.account.common.events.AccountOpenedEvent;
import com.arka99.account.common.events.FundsDepositedEvent;
import com.arka99.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void handle(AccountOpenedEvent openedEvent);
    void handle(FundsDepositedEvent depositedEvent);
    void handle(FundsWithdrawnEvent withdrawnEvent);
    void handle(AccountClosedEvent closedEvent);
}
