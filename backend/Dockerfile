FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/Arttab-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} arttab.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=ec2", "arttab.jar"]