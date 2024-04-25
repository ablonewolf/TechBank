### Definition of Microservice:
- microservices are small, loosely coupled applications or services that can fail independently from each other.
- when a microservice fails, only a single process or function in the system should become unavailable, while the rest of the system remains unaffected.

### Principles of Microservices:
- microservices should not share code or data.
- avoid unnecessary coupling between services and software components.
- independence and autonomy are more important than code re-usability.
- each microservice should be rresponsible for a single function or process in a system.
- microservices should not communicate directly with each other. Rather, they should make use of event/message bus to communicate with each other.
