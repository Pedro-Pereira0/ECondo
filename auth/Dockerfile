FROM maven:4.0.0-rc-5-amazoncorretto-25-debian
WORKDIR /app/
COPY . /app/
RUN mvn clean package -DskipTests
CMD ["mvn", "spring-boot:run"]