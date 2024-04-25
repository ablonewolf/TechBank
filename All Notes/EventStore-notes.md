### Event Store:
    
- an event store is a database to store data as immutable events over time.
- an event store is a key-enabler in event sourcing.
- key considerations in designing an event store:
    - an event store must be only an append only store, update or delete operation are not allowed.
    - each event that is saved should represent the version or state of an aggregate at any given point in time.
    - events should be stored in chronological order, and new events should be appended to the previous event.
    - the state of the aggregate should be recreatable by replaying the event store.
    - implement optimistic concurrency control.
