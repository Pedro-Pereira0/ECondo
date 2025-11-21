FROM maven:4.0.0-rc-5-amazoncorretto-25-debian
WORKDIR /app/
COPY . /app/
EXPOSE 8080
RUN mvn clean package -DskipTests
CMD ["mvn", "spring-boot:run"]
#CMD ["java", "-jar", "/auth/target/auth-0.0.1-SNAPSHOT.jar"]