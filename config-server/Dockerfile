FROM openjdk:11
COPY /target/*.jar config-server.jar
COPY /scripts/configserver_git_init.sh init.sh
RUN ./init.sh
ENTRYPOINT ["java","-jar","./config-server.jar"]