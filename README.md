# Camel Service

### Building/Testing

To build the application and associated docker image, run the following command from the projects root directory.

`./mvnw clean verify`

### Running the application
To run the application:

`./mvnw spring-boot:run`

This will start the application running locally on port 8080. 


### Running the docker image
To run the docker image:

`docker-compose up`

This will start the application running locally on port 8080.  

### API Documentation

The API documentation is available, when the application is running, at http://localhost:8080/api-docs.


### Example

`POST -> http://localhost:8080/api/users`

`
{
    "firstName": "martin",
    "lastName": "walsh"
}
`