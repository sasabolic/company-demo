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
    
## API Reference

API reference documentation is available at http://localhost:8080/swagger-ui.html.

### Example API Calls

Example API calls for the company and owner resource (the database is prepopulated with owners data):

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


* To create new company: 

    Request:
    
        curl -X POST "http://localhost:8080/companies" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"GUBI A/S\", \"address\": \"Klubiensvej 7-9 / Pakhus 53, 2150 Frihavnen\", \"city\": \"Copenhagen\", \"country\": \"Denmark\", \"email\": \"office@gubi.com\", \"phone_number\": \"+45 3332 6368\", \"owners\": [ 1 ]}"

    Response:
    
    
        Header:
            Status: 201 Created
            Location: http://localhost:8080/companies/1
        Body:
        
    
* To get details of company with id '1' 

    Request:
    
        curl -X GET "http://localhost:8080/companies/1" -H "accept: */*"

    Response:
    
    
        Header:
            Status: 200 OK
    
        Body:
        
            {
                "id": 1,
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
                    "name": "GUBI A/S",
                    "address": "Klubiensvej 7-9 / Pakhus 53, 2150 Frihavnen",
                    "city": "Copenhagen",
                    "country": "Denmark"
                },
                {
                    "id": 2,
                    "name": "Tesla",
                    "address": "3500 Deer Creek Road, CA 94304",
                    "city": "Palo Alto",
                    "country": "USA"
                },
                {
                    "id": 3,
                    "name": "Telenor ASA",
                    "address": "Snarøyveien 30, N-1360 Fornebu, Norway",
                    "city": "Oslo",
                    "country": "Norway"
                }
            ]

* To update company with id '1': 

    Request:
    
        curl -X PUT "http://localhost:8080/companies/1" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"GUBI A/S\", \"address\": \"Klubiensvej 7-9 / Pakhus 53, 2150 Frihavnen\", \"city\": \"Copenhagen\", \"country\": \"Denmark\", \"email\": \"new_office@gubi.com\", \"phone_number\": \"+45 3332 6368\", \"owners\": [ 1 ]}"

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
    

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - The web framework used
* [Gradle](https://gradle.org) - Build tool
* [Docker](https://docs.docker.com/install/) - Container packaging


