# messenger-rabbit-mq
1) docker pull rabbitmq
2) docker run -d --hostname rabbit-mq-instance --name rabbit-1 -p 15672:15672 -p 5672:5672 rabbitmq:3-management
