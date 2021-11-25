FROM openjdk:17.0.1-jdk-buster

WORKDIR /app
COPY ./target/impianto_balneare-0.0.1-SNAPSHOT.jar /app

LABEL version="1.0"
LABEL description="IDS_Project"

EXPOSE 8080/tcp

CMD ["java", "-jar", "impianto_balneare-0.0.1-SNAPSHOT.jar"]
