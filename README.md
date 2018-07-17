# Company Demo

System for company data.

## Getting Started

These instructions will explain you how to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

In order to build the project you will have to have [Java 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/index.html), [Docker 17.12.0+](https://docs.docker.com/install/)
and [Docker Compose](https://docs.docker.com/compose/install/) installed on your machine.

You might have some of the required software already installed on you machine. To confirm it, please run the following commands
in your terminal window:

For Java:

    java --version

For Docker:

    docker --version

For Docker Compose:

    docker-compose --version 
    
### Installing and Running

Follow the steps below to quickly bring up the infrastructure. 

> It is assumed that you've opened your terminal window and navigated to the directory of this document.

#### Start all the services with docker-compose

To start all the services run the following command from your terminal window:

    ./run.sh

This script will execute tests and package the back-end services, build the Docker images and install them 
in your local Docker image registry. All of the containers will then be run using **docker-compose**.

> **Important:** 
>Make sure that you have the following ports available in your local machine: **8080** and **3306**. These are
>the ports used by **company-demo** and **MySQL**, respectively.

After you have run the `./run.sh` for the first time, all of the services will be containerized. Therefore, for every subsequent infrastructure bootstrap, it is sufficient to run **docker-compose**: 

    docker-compose up

## Running the tests

    ./gradlew clean test
    
## Running the integration tests

    ./gradlew clean integrationTest
    
## API Reference

API reference documentation is available at http://localhost:8080/swagger-ui.html.

### Example API Calls

Example API calls for the company and owner resource (the database is prepopulated with owners and company data):

* To return all owners: 

    Request:
    
        curl -X GET "http://localhost:8080/owners" -H "accept: */*"
    
    Response:
    
    
        Header:
            Status: 200 OK
                
        Body:

            [
              {
                "id": 1,
                "name": "Elon Musk"
              },
              {
                "id": 2,
                "name": "Richard Hendricks"
              },
              {
                "id": 3,
                "name": "Gavin Belson"
              }
            ]

* To search for owners with name containing string: 'el': 

    Request:
    
        curl -X GET "http://localhost:8080/owners?name=el" -H "accept: */*"

    Response:
    
        [
            {
                "id": 1,
                "name": "Elon Musk"
            },
            {
                "id": 3,
                "name": "Gavin Belson"
            }
        ]
    

* To create new company: 

    Request:
    
        curl -X POST "http://localhost:8080/companies" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"GUBI A/S\", \"address\": \"Klubiensvej 7-9 / Pakhus 53, 2150 Frihavnen\", \"city\": \"Copenhagen\", \"country\": \"Denmark\", \"email\": \"office@gubi.com\", \"phone_number\": \"+45 3332 6368\", \"owners\": [ 1 ]}"

    Response:
    
    
        Header:
            Status: 201 Created
            Location: http://localhost:8080/companies/3
        Body:
        
    
* To get details of company with id '3' 

    Request:
    
        curl -X GET "http://localhost:8080/companies/3" -H "accept: */*"

    Response:
    
    
        Header:
            Status: 200 OK
    
        Body:
        
            {
                "id": 3,
                "name": "GUBI A/S",
                "address": "Klubiensvej 7-9 / Pakhus 53, 2150 Frihavnen",
                "city": "Copenhagen",
                "country": "Denmark",
                "email": "office@gubi.com",
                "owners": [
                    {
                        "id": 1,
                        "name": "Elon Musk"
                    }
                ],
                "phone_number": "+45 3332 6368"
            }

* To return all companies:

    Request:
    
         curl -X GET "http://localhost:8080/companies" -H "accept: */*"

    Response:
    
    
        Header:
            Status: 200 OK
    
        Body:
    
            [
                {
                    "id": 1,
                    "name": "Telenor ASA",
                    "address": "Snarøyveien 30, N-1360 Fornebu, Norway",
                    "city": "Oslo",
                    "country": "Norway"
                },
                {
                    "id": 2,
                    "name": "Apple",
                    "address": "One Apple Park Way, CA 95014",
                    "city": "Cupertino",
                    "country": "USA"
                },
                {
                    "id": 3,
                    "name": "GUBI A/S",
                    "address": "Klubiensvej 7-9 / Pakhus 53, 2150 Frihavnen",
                    "city": "Copenhagen",
                    "country": "Denmark"
                }
            ]

* To update company with id '1': 

    Request:
    
        curl -X PUT "http://localhost:8080/companies/3" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"GUBI A/S\", \"address\": \"Klubiensvej 7-9 / Pakhus 53, 2150 Frihavnen\", \"city\": \"Copenhagen\", \"country\": \"Denmark\", \"email\": \"new_office@gubi.com\", \"phone_number\": \"+45 3332 6368\", \"owners\": [ 1 ]}"

    Response:
    
    
        Header:
            Status: 204 No Content
    
        Body:
    

    
* Get company details for non-existing company

    Request:
    
        curl -X GET "http://localhost:8080/companies/4" -H "accept: */*"

    Response:
    
        
        Header:
            Status: 404 Not Found
           
        Body:
        
            {
              "timestamp": "2018-06-18T12:40:00.258",
              "status": 404,
              "error": "Not Found",
              "exception": "com.example.companydemo.company.CompanyNotFoundException",
              "message": "Company with id '4' does not exist",
              "errors": null
            }
   
* Create non-unique company (in case company with same name already exists): 

    Request:
    
        curl -X POST "http://localhost:8080/companies" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"GUBI A/S\", \"address\": \"Klubiensvej 7-9 / Pakhus 53, 2150 Frihavnen\", \"city\": \"Copenhagen\", \"country\": \"Denmark\", \"email\": \"office@gubi.com\", \"phone_number\": \"+45 3332 6368\", \"owners\": [ 1 ]}"

    Response:
    
        
        Header:
            Status: 409 Conflict
           
        Body:
        
            {
              "timestamp": "2018-06-18T12:57:20.2568",
              "status": 409,
              "error": "Conflict",
              "exception": "com.example.companydemo.company.CompanyUniqueViolationException",
              "message": "Company with name 'GUBI A/S' located in 'Denmark' already exits",
              "errors": null
            }
    
* Create company where required fields are missing (failed validation): 

    Request:
    
        curl -X POST "http://localhost:8080/companies" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"\", \"address\": \"Klubiensvej 7-9 / Pakhus 53, 2150 Frihavnen\", \"city\": \"Copenhagen\", \"country\": \"Denmark\", \"email\": \"office@gubi.com\", \"phone_number\": \"+45 3332 6368\", \"owners\": [ 1 ]}"

    Response:
    
        
        Header:
            Status: 400 Bad Request
           
        Body:
        
            {
              "timestamp": "2018-06-18T12:57:20.2568",
              "status": 400,
              "error": "Bad Request",
              "exception": "org.springframework.web.bind.MethodArgumentNotValidException",
              "message": "Validation failed",
              "errors": [
                "Name cannot be empty"
              ]
            }

## Considerations

The security layer has not been implemented for this demo project. 

For securing the service the OAuth authorization flow can be used, together with the JWT access token.

OAuth's authorization server would contain user identities and credentials and through it
client applications (web, desktop, mobile etc.) could obtain authorization to access the resources stored in our demo application (resource server).
By using JWT we can easily share authentication and authorization information between multiple service in our micro-service stack. With JWT there 
is no need of keeping track of user sessions in the distributed cache or database, which can significantly reduce costs of the cloud stack. 

For achieving the high availability of the service, the service gateway (e.g. Zuul) and service discovery (e.g. Eureka) could be used.
Each instance of the demo project service will register itself with the Eureka server. Clients using the service will access it through
Zuul gateway, which will provide the routing, resiliency, security etc. If for any reason one of the instances of the demo project crashes, 
the Zuul will delegate the client calls to the rest of the healthy instances. Where failure is inevitable circuit breaker (e.g Hystrix) can be used to enable resilience and fault tolerance to distributed systems.

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - The web framework used
* [Gradle](https://gradle.org) - Build tool
* [Docker](https://docs.docker.com/install/) - Container packaging


