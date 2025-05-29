# 1) Собираем JAR
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2) Финальный образ с JRE + LibreOffice
FROM eclipse-temurin:21-jre-jammy
# Устанавливаем LibreOffice Headless
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
        libreoffice-core libreoffice-writer libreoffice-calc \
    && rm -rf /var/lib/apt/lists/*

# Рабочая папка
WORKDIR /app

# Копируем JAR
COPY --from=build /app/target/document-converter-service-0.0.1-SNAPSHOT.jar app.jar

# Точка монтирования для документов
VOLUME /documents

# Запускаем приложение
ENTRYPOINT ["java","-jar","/app/app.jar"]
