FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src

ENV JAVA_OPTS="-Xmx512m -Xms256m"
 
CMD ["./mvnw", "spring-boot:run"]