package com.arka99.account.cmd.api.command;

import com.arka99.account.common.dto.AccountType;
import com.arka99.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
