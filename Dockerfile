# Stage 1: Build ứng dụng bằng Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml và source code
COPY pom.xml .
COPY src ./src

# Build file executable jar (bỏ qua tests trong quá trình container build)
RUN mvn clean package -DskipTests

# Stage 2: Chạy ứng dụng bằng Java 21 JRE Alpine
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Tạo group và user không có quyền root (UID/GID 10001)
RUN addgroup -S -g 10001 spring && \
    adduser -S -D -H -u 10001 -G spring spring

# Sao chép file JAR từ giai đoạn build
COPY --from=build /app/target/*.jar app.jar

# Tạo thư mục log và cấp quyền cho user spring
RUN mkdir -p /app/logs && \
    chown -R spring:spring /app

# Ứng dụng sử dụng cổng 8080
EXPOSE 8080

# Chạy ứng dụng bằng user không phải root
USER 10001:10001

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
