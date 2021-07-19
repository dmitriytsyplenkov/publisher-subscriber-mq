FROM openjdk:11
COPY /target/*.jar publisher.jar
ENTRYPOINT ["java","-jar","./publisher.jar","--spring.cloud.config.uri=http://config-server:8888","--spring.rabbitmq.host=myrabbit"]