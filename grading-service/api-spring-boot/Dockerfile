# ── Étape 1 : Build avec Maven ──────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copier le pom.xml et télécharger les dépendances (mise en cache Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier les sources et compiler
COPY src ./src
RUN mvn package -DskipTests -B

# ── Étape 2 : Image d'exécution légère ──────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
