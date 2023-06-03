package com.arka99.account.query.infrastructure.handlers;

import com.arka99.account.common.events.AccountClosedEvent;
import com.arka99.account.common.events.AccountOpenedEvent;
import com.arka99.account.common.events.FundsDepositedEvent;
import com.arka99.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent openedEvent);
    void on(FundsDepositedEvent depositedEvent);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent closedEvent);
}
