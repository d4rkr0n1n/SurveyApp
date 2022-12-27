FROM maven:3.8.3-openjdk-17

WORKDIR /SurveyApp
COPY . .
RUN mvn clean install
EXPOSE 8080
CMD mvn spring-boot:run