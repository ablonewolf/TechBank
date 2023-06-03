package com.arka99.account.query.infrastructure.handlers;

import com.arka99.account.common.events.AccountClosedEvent;
import com.arka99.account.common.events.AccountOpenedEvent;
import com.arka99.account.common.events.FundsDepositedEvent;
import com.arka99.account.common.events.FundsWithdrawnEvent;
import com.arka99.account.query.domain.AccountRepository;
import com.arka99.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void handle(AccountOpenedEvent openedEvent) {
        var bankAccount = BankAccount.builder()
                .id(openedEvent.getId())
                .accountHolder(openedEvent.getAccountHolder())
                .creationDate(openedEvent.getCreatedDate())
                .accountType(openedEvent.getAccountType())
                .balance(openedEvent.getOpeningBalance())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void handle(FundsDepositedEvent depositedEvent) {
        var bankAccount = accountRepository.findById(depositedEvent.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + depositedEvent.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void handle(FundsWithdrawnEvent withdrawnEvent) {
        var bankAccount = accountRepository.findById(withdrawnEvent.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - withdrawnEvent.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void handle(AccountClosedEvent closedEvent) {
        accountRepository.deleteById(closedEvent.getId());
    }
}
