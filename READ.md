# Spring Boot Docker Demo

This is a simple Spring Boot application with one endpoint:

"/welcome"
which returns: "Welcome to docker"

The application is containerized using Docker and runs on Docker Desktop.


# Technologies Used

- Java 21
- Spring Boot
- Docker
- Eclipse Temurin OpenJDK 21 image for java

# Dockerfile

```dockerfile
FROM eclipse-temurin:21

LABEL maintainer="Vinayak Warad"

ADD target/dockTest-0.0.1-SNAPSHOT.jar dockTest.jar

ENTRYPOINT ["java","-jar","dockTest.jar"]
```

---

# Prerequisites

Install the following before running the application:

- Java
- Maven
- Docker Desktop

Useful Links:

Docker Desktop:
https://www.docker.com/products/docker-desktop/

Spring Initializr:
https://start.spring.io/

---

# Step 1: Build Spring Boot JAR

Run the following command from the project root directory where `pom.xml` exists.

Open:

```text
CMD / PowerShell / Terminal
```

Command:

```bash
mvn clean package
```

This creates the JAR file inside:
target/

Example:
target/dockTest-0.0.1-SNAPSHOT.jar


# Step 2: Build Docker Image

Run the following command from the project root directory where the `Dockerfile` exists.

Command:

```bash
docker build -t docktest-app .
```

Explanation:

| Command Part | Meaning |
|---|---|
| docker build | Builds Docker image |
| -t docktest-app | Image name |
| . | Current directory |

# Step 3: Verify Docker Image

Command:

```bash
docker images
```

This shows all available Docker images.

Example Output:

```text
REPOSITORY      TAG       IMAGE ID
docktest-app    latest    abc123xyz
```

# Step 4: Run Docker Container

Command:

```bash
docker run -p 8080:8080 docktest-app
```

Explanation:

| Command Part | Meaning |
|---|---|
| docker run | Starts container |
| -p 8080:8080 | Maps local port to container port |
| docktest-app | Docker image name |

---

# Step 5: Access Application

Open browser:

```text
http://localhost:8080/welcome
```

Expected Response:

```text
Welcome to docker
```

---

# Docker Container Commands

## View Running Containers

```bash
docker ps
```

---

## View All Containers

```bash
docker ps -a
```

---

## Stop Running Container

```bash
docker stop <container-id>
```

Example:

```bash
docker stop a1b2c3d4
```

---

## Start Existing Container

```bash
docker start <container-id>
```

---

## Remove Container

```bash
docker rm <container-id>
```

---

## Remove Docker Image

```bash
docker rmi docktest-app
```

---

# Project Flow

```text
Spring Boot Application
        ↓
Build JAR using Maven
        ↓
Create Docker Image
        ↓
Run Docker Container
        ↓
Access Application on Browser
```

---

# Author

Vinayak Warad