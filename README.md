# Spring Boot Online Banking System

This project is designed as a hands-on exercise for Spring. The primary objective is to create an online banking system using a combination of technologies, including Spring Boot, Spring Security, Spring Data JPA, Spring Data REST, JUnit Test with DevOps Docker container. The database implementation employs PostgreSQL.

## Features

### User-Front

The User-Front component serves as the user interface of the online banking system. It comprises the following modules:

- **User Signup/Login:** Allows users to create accounts and log in.
- **Account Management:** Facilitates account balance checks, deposits, and withdrawals.
- **Transfer:** Provides a platform for initiating fund transfers between accounts.
- **Appointment:** Enables users to schedule appointments related to their accounts.
- **Transaction History:** Displays a record of transactions associated with the user's account.
- **User Profile:** Allows users to manage their profile information.

### Admin-Portal

The Admin-Portal is primarily designed for administrators and includes the following modules:

- **User Account Management:** Enables administrators to oversee and manage user accounts.
- **Appointment Management:** Provides tools for administrators to manage appointments.

### System Detail Diagram

![Screenshot](screenshot/system_detail_diagram.png)

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Data REST
- JWT
- JUnit Test
- PostgreSQL (Database)
- Docker


## Usage

### Docker
This app can install and deploy in a Docker container.

By default, the Docker will expose port 8080, so change this within the Dockerfile if necessary. When ready, simply use the docker-compose.yml to build the image.

```sh
git clone https://github.com/cliffzhong/bankCompanyProject.git
cd bankCompanyProject
docker-compose up
```

### Login Online Banking Web App

http://localhost:8080/

| username | password |
| ------ | ------ |
| admin | password |

