# Etapa 1: Compilación con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar los archivos necesarios
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto y generar el JAR
RUN mvn clean package -DskipTests

#️⃣ Etapa 2: Ejecución del JAR
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=builder /app/target/tienda-coleccionables-1.0-SNAPSHOT.jar /app/app.jar
# Exponer el puerto Spark (por defecto 4567)
EXPOSE 4567

# Ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]