package com.arka99.account.cmd.domain;

import com.arka99.cqrs.core.domain.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStoreRepository extends MongoRepository<EventModel, String> {
    @Override
    List<EventModel> findAll();

    List<EventModel> findAllByAggregateIdentifier(String aggregateIdentifier);
}
