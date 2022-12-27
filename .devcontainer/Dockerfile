FROM docker.io/d4rkr0n1n/surveyapp

WORKDIR /SurveyApp
COPY . .
RUN mvn clean install
EXPOSE 8080
CMD mvn spring-boot:run