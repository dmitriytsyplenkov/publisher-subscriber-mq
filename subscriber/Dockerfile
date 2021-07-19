FROM openjdk:11
COPY /target/*.jar subscriber.jar
ENTRYPOINT ["java","-jar","./subscriber.jar","--spring.cloud.config.uri=http://config-server:8888","--spring.rabbitmq.host=myrabbit"]