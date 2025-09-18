# ---------- Build ----------
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

# Cache de dependencias
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn
RUN ./mvnw -q -DskipTests dependency:go-offline || true

# Código
COPY src ./src

# Empaquetar Pawtify (genera ...-SNAPSHOT.jar y .original)
RUN ./mvnw -q -DskipTests package

# ---------- Run ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos solo el jar ejecutable, no el .original
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar

# Puerto interno por defecto. Render inyecta $PORT
ENV PORT=8080
ENV JAVA_OPTS=""

EXPOSE 8080

# Arranque
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT} -jar app.jar"]
