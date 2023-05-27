package com.arka99.cqrs.core.infrastructure;

import com.arka99.cqrs.core.commands.BaseCommand;
import com.arka99.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void send(BaseCommand command);
}
