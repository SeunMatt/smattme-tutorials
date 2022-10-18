Spring Boot Worker
==================
This codebase is a demonstration of how to use a single codebase to 
consume events from multiple queues while providing capacity to scale each instance independently

The `SimpleMessageListenerContainer` is configured to listen to queues that are coming from

a configuration properties. This means we can create different profiles that'll have different 

set of queues. This way, we can run the same codebase and scale them independently.

For example, let's say we have the following queues:

- SMS_QUEUE
- EMAIL_QUEUE
- CUSTOMERIO_QUEUE

We can create the following application properties file:

- application.yml - the default profile will have ALL the queues.
```yaml
smattme:
  worker-config:
    queue-names:
      - SMS_QUEUE
      - EMAIL_QUEUE
      - CUSTOMERIO_QUEUE
```

- application-worker-sms.yml: This profile will contain just the SMS_QUEUE and process events for that queue alone
```yaml
smattme:
  worker-config:
    queue-names:
      - SMS_QUEUE
```

We can deploy this same codebase to EKS with profile `worker-sms` and set the instance count to 3

This will let us scale as needed while maintain a low overhead in terms of codebase maintenance


Architecture
=============
We have a `MainEventListener` class that is connected directly to the `SimpleMessageListenerContainer` and receives

ALL the events for all the queues configured in the profile. 

This `MainEventListener` then delegates to different implementations of `ChildEventListener` based on the 
`consumerQueue` of the incoming `org.springframework.amqp.core.Message`.

Each implementation of `ChildEventListener` is a Spring managed bean and thus can be easily loaded from the 
Spring context - this makes the code cleaner and avoids autowiring every implementation into the
`MainEventListener` class

In cases where there's no implementation for the incoming queue, the `MainEventListener` will
simply raise a `RuntimeException`



Author
======
Seun Matt