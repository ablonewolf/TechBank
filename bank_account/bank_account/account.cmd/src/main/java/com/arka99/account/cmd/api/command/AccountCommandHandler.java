package com.arka99.account.cmd.api.command;

import com.arka99.account.cmd.domain.AccountAggregate;
import com.arka99.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler {
    @Autowired
    private EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handle(OpenAccountCommand openAccountCommand) {
        var accountAggregate = new AccountAggregate(openAccountCommand);
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(DepositFundsCommand depositFundsCommand) {
        var aggregate = eventSourcingHandler.getById(depositFundsCommand.getId());
        aggregate.depositFunds(depositFundsCommand.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand withdrawFundsCommand) {
        var aggregate = eventSourcingHandler.getById(withdrawFundsCommand.getId());
        if (withdrawFundsCommand.getAmount() > aggregate.getBalance()) {
            throw new IllegalStateException("Withdrawal declined. Insufficient Funds.");
        }
        aggregate.withdrawFunds(withdrawFundsCommand.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand closeAccountCommand) {
        var aggregate = eventSourcingHandler.getById(closeAccountCommand.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }
}
