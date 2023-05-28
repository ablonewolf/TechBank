package com.arka99.account.cmd.infrastructure;

import com.arka99.account.cmd.domain.AccountAggregate;
import com.arka99.account.cmd.domain.EventStoreRepository;
import com.arka99.cqrs.core.domain.EventModel;
import com.arka99.cqrs.core.events.BaseEvent;
import com.arka99.cqrs.core.exceptions.AggregateNotFoundException;
import com.arka99.cqrs.core.exceptions.ConcurrencyException;
import com.arka99.cqrs.core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findAllByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 &&
                eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (persistedEvent == null) {
//                toDo: produce event to Kafka
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findAllByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account ID provided.");
        }
        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }
}
