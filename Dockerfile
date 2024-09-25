FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências do Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

# Compila a aplicação
RUN mvn clean package -DskipTests

# Usa uma imagem mais leve para rodar a aplicação
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR gerado na fase anterior
COPY --from=build /app/target/*.jar creditguard.jar

# Expõe a porta que a aplicação usará
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "creditguard.jar"]
