# ---------- Build ----------
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

# Cache de dependencias
COPY pom.xml .
# Si usas Maven Wrapper, copia estos dos también. Si no existen, no pasa nada.
COPY mvnw ./
COPY .mvn .mvn
RUN mvn -q -DskipTests dependency:go-offline || true

# Código fuente
COPY src ./src

# Empaquetar Pawtify
RUN mvn -q -DskipTests package

# ---------- Run ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos el jar generado de Pawtify sin amarrar el nombre exacto
COPY --from=build /app/target/*.jar app.jar

# Puerto interno por defecto. Puedes cambiarlo con -e PORT=80
ENV PORT=8080
ENV JAVA_OPTS=""

EXPOSE 8080

# Arranque. Forzamos server.port a $PORT por si tu application.properties tenía 80
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT} -jar app.jar"]
