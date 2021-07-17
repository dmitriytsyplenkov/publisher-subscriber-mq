## Publisher-Subscriber Microservice App with MQ

### Config-server
    Common properties(queue name, exchange name, routing keys etc) stored in file(local git repo) 
    And this properties accessed via config-server by other services
    To init repo you can run [configserver_git_init.sh](scripts/configserver_git_init.sh) 

### RabbitMQ
    Also you need to have RabbitMQ installed aswell
    Here you can find file to take it from docker repo [docker-compose.yml](scripts/docker-compose.yml)
    In this app default settings of RabbitMQ used(port=5672 etc) 
    But you can reconfigure it in common file which is accessed via Config server
  
### Publisher
    Publisher has an endpoint to produce message publishing in RabbitMQ via HTTP Post requests in JSON format(converting object into JSON)
    Also it has some multithreaded message generating(it creates some random messages in 5 threads with 15 sec delay (by default))
    There are two types of messages which is published in same queue but with different binding keys  
### Subscriber
    Subscriber listens RabbitMQ and persists messages in different tables for different binding keys
    It uses in-memory H2 database(it could be easily replaced to whatever SQL DB)
            