# Open Weather API

## Getting started

> Requirements: Java14, Maven, Docker

> After cloning the project, go to project path

> Build app

    mvn clean package

> Open another terminal and run following command. It will start a local redis service

    docker-compose -f docker-compose-local.yaml up

> Add api key for Open weather api to env variables

    OPEN_WEATHER_APIKEY=YOUR_API_KEY

> Run app

    mvn spring-boot:run

> You can either use api client to send requests (eg. Postman) or use Swagger Doc

    For Swagger go to http://localhost:8080/swagger-ui.html
    For manual request
      GET localhost:8080/external-api/v1/weather/current?cityName=paris

> To shut down redis service

    docker-compose -f docker-compose-local.yaml down
