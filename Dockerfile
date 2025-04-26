# Stage 1: Build (optional nếu bạn build ngoài)
# FROM maven:3.8.5-openjdk-17 AS build
# WORKDIR /app
# COPY . .
# RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

# Thêm file JAR vào image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Mở port mà Spring Boot sử dụng (mặc định 8080)
EXPOSE 7777

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app.jar"]
