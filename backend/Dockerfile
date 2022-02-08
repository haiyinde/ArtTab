FROM openjdk:11-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/Arttab-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} arttab.jar
ENTRYPOINT ["java", "-jar", "arttab.jar"]