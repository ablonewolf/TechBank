package com.arka99.account.cmd.api.command;

public interface CommandHandler {
    void handle(OpenAccountCommand openAccountCommand);

    void handle(DepositFundsCommand depositFundsCommand);

    void handle(WithdrawFundsCommand withdrawFundsCommand);

    void handle(CloseAccountCommand closeAccountCommand);
}
