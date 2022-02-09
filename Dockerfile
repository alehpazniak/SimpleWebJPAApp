FROM maven:3.8.1-openjdk-11-slim

WORKDIR /app
COPY . /app
RUN mvn clean install
CMD java -jar target/simplewebapp-0.0.1-SNAPSHOT.jar