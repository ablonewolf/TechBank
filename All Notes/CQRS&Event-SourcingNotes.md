### CQRS
- Stands for Command Query Responsibility Segregation.
- It is a software design pattern that suggests dividing an application into two parts: Command and Query.
- The Command part is responsible for altering the state of objects or entities.
- The Query part is responsible for returning the state of objects or entities.

### Why Use CQRS:
- Data is often more frequently queried than altered, or vice versa.
- Segregating commands and queries enables us to optimize each for high performance.
- Read and write representations of data often differ substantially.
- Executing command and query operations on the same model can cause data contention.
- Segregating read and write concerns enables us to manage read and write security separately.

### Event Sourcing:
- Defines an approach to store the changes made to an object or entity as a sequence of immutable events instead of saving the current state of the object or entity.

### Benefits of Event Sourcing:
- Event store provides a complete log of every state change.
- The state of an object/aggregate can be recreated by replaying the event store.
- Improves write performance, as all event types are simply appended to the event store without update or delete operations.
- In case of failure, the event store can be used to restore the read database.
