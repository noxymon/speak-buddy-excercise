FROM eclipse-temurin:21-alpine AS builder
LABEL stage=builder
RUN mkdir -p /app
WORKDIR /app
COPY . .

RUN chmod +x /app/mvnw
RUN /app/mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

RUN mkdir -p /app
RUN mkdir -p /app/data
RUN mkdir -p /app/data/audio

WORKDIR /app

COPY --from=builder /app/target/sound-receiver-api-0.0.1-SNAPSHOT.jar /app/sound-receiver-api.jar
COPY --from=builder /app/data/*.db /app/data/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sound-receiver-api.jar"]