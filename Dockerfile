
FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw package -DskipTests


FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Informa ao Docker que a aplicação dentro deste contêiner vai "escutar" na porta 8080.
EXPOSE 8080

# O comando que será executado quando o contêiner iniciar.
ENTRYPOINT ["java","-jar","app.jar"]