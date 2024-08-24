
# Getting Started
this project is a simple store application that allows users to upload and download files. which has a simple JWT authentication mechanism.
there are 3 related projects for this application:

* [springnuxt2024-storeapi-backend](https://github.com/pingkunga/springnuxt2024-storeapi-backend) >>> this project
* [springnuxt2024-storeapi-frontend](https://github.com/pingkunga/springnuxt2024-storeapi-frontend)
* [springnuxt2024-storeapi-infra](https://github.com/pingkunga/springnuxt2024-storeapi-infra)

# Features

* Spring Boot CRUD with JPA
* Spring Boot Security - Simple JWT Authentication Token
* Spring Boot File Upload / Download
* Postgres Database

# How to Build & Run 

* Build 

```
docker build -t store-back:1.0.0 .
```

* Run 
```
docker run -d -p 8081:8081 -v ${pwd}/application.properties:/app/application.properties  -v ${pwd}/uploads:/app/uploads --name store-app-back store-back:1.0.0
```