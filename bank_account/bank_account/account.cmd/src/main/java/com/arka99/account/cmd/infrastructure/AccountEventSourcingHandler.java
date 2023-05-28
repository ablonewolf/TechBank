package com.arka99.account.cmd.infrastructure;

import com.arka99.account.cmd.domain.AccountAggregate;
import com.arka99.cqrs.core.domain.AggregateRoot;
import com.arka99.cqrs.core.events.BaseEvent;
import com.arka99.cqrs.core.handlers.EventSourcingHandler;
import com.arka99.cqrs.core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var accountAggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            accountAggregate.replayEvent(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            accountAggregate.setVersion(latestVersion.get());
        }
        return accountAggregate;
    }
}
