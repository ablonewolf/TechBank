package com.arka99.account.cmd.domain;

import com.arka99.account.cmd.api.command.OpenAccountCommand;
import com.arka99.account.common.events.AccountClosedEvent;
import com.arka99.account.common.events.AccountOpenedEvent;
import com.arka99.account.common.events.FundsDepositedEvent;
import com.arka99.account.common.events.FundsWithdrawnEvent;
import com.arka99.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;

    public double getBalance() {
        return balance;
    }

    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be deposited into a closed account");
        } else if (amount <= 0) {
            throw new IllegalStateException("The deposit amount must be greater than zero.");
        } else {
            raiseEvent(FundsDepositedEvent.builder()
                    .id(this.id)
                    .amount(amount)
                    .build());
        }
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be withdrawn from a closed account");
        } else if (this.balance - amount < 0) {
            throw new IllegalStateException("Not sufficient fund in the account.");
        }
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!this.active) {
            throw new IllegalStateException("Account is already closed.");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
