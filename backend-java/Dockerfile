FROM openjdk:17-jdk-slim AS build

WORKDIR /app
COPY backend-java /app

RUN apt-get update && apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
