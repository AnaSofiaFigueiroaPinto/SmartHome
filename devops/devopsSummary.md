# Containerization and application deployment - Group 5

### Table of Contents
- [Introduction](#introduction)
- [Containerization](#containerization)
  - [Structure](#structure) 
  - [Provisioned files](#provisioned-files)
  - [Container orchestration](#container-orchestration)
- [Server deployment](#server-deployment) 

## Introduction
This report focuses on the containerization and deployment of the developed Smart Home application.  
The application is divided into three main components: the frontend made in React, backend using Spring Framework.
The application was containerized using Docker, and has 3 containers: 1 for the frontend, 1 for the backend and 1 for the database.
When deployed in ISEP's DEI servers, the application uses 3 virtual servers, 1 for the frontend and backend, 1 for the database and 1 for Adminer.

## Containerization:

### Structure:
The application was containerized using Docker, and was divided into 3 containers:
- Frontend: The [Dockerfile](DockerfileFrontend) uses a node base image. The repository is cloned and the frontend is compiled and deployed.
- Backend: The [Dockerfile](DockerfileBackend) uses a tomcat10/jdk17 base image. The repository is cloned and [files are provisioned](docker_img_provisions) to facilitate container deployment, tomcat deployment and database connection.
- Database: The [Dockerfile](DockerfileDatabase) uses a MariaDB base image. Upon container creation, a  [SQL file](docker_img_provisions/base_data.sql) is provided to create needed base data.  

The containers clone the repository as is, in order to have the latest contributions by team members.
Container images can be found [here](https://hub.docker.com/repository/docker/filipaba/smarthome/general).

### Provisioned files:
The provisioned files are:
- [application.properties](docker_img_provisions/application.properties): Used to override the default application properties in order to communicate with the database.
- [base_data.sql](docker_img_provisions/base_data.sql): Used to create a starter set of data in the database.
- [pom.xml](docker_img_provisions/pom.xml): Used to change archive format to war and add mariaDB dependency.
- [CorsConfig.java](docker_img_provisions/CorsConfig.java): Used to allow frontend to communicate with backend.
- [ServletInitializer.java](docker_img_provisions/ServletInitializer.java): Used to configure the application as a servlet.
- [id_rsa](docker_img_provisions/id_rsa): Used to clone the repository without needing to authenticate.

### Container orchestration:
A Docker [Compose file](compose.yaml) was created to orchestrate the deployment of all containers.  
To build the images, care must be taken to avoid using cached data, so the build command should be `docker-compose build --no-cache` and then `docker-compose up` to deploy the containers.  
The frontend is available at `localhost:3000`, the backend at `localhost:8080`. All Dockers run in a private network named `app-network` using bridge mode.

## Server deployment:
The application was deployed on ISEP's DEI servers.
Originally the plan was to deploy the containerized application on a virtual server running a VM with Alpine Linux.  
This would allow the application to stay up to, as stated in the [containerization guide](#structure), however due to unforeseen network issues, Docker is unable to clone the repository.

An alternative was found by deploying the application on 1 virtual server for both the frontend and backend and 2 virtual servers for the database and Adminer.
The database was deployed using a MySQL container as MariaDB was not available.

The file are manually compiled locally and then uploaded to the server using SFTP with Filezilla.

The frontend server is run on Nginx, the backend on Tomcat and the database on MySQL. 

The application can be viewed [here](http://10.9.21.205/), however a ISEP VPN connection is needed.




