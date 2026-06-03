# Spring Boot Docker Demo

This is a simple Spring Boot application with one endpoint:

"/welcome"
which returns: "Welcome to docker--devTestEnvt--environment"

1. The application is containerized using Dockerfile and image is pushed to dockerhub (vinayakwarad/dock-test:v1 )
2. Minikube is installed in local and it is brought up which creates local kubernetes cluster with 1 node.
3. Name space "dev" is created and deployment yml file is applied to cluster to start 2 pods of dock-test application
   which pulls above pushed image and 2 pods/containers of same image are created
4. Steps to create IP that we can access out end point
   - minikube ip                -> 192.168.49.2
   - kubectl get svc -n dev     -> dock-test-k8s   NodePort   10.101.x.x   80:31245/TCP
     Meaning:
       service port = 80
       node port = 31245
   - Final URL - http://192.168.49.2:31245
   - If your controller endpoint is: If your controller endpoint is:@GetMapping("/hello")
     Final URL -> "http://192.168.49.2:31245/hello"
   - URL = Minikube-IP + NodePort + Endpoint
5. *** VIP NOTE ********
   - We expect to hit "http://192.168.49.2:31245/hello" But this does not work on windows local.
   - So instead we have to use "minikube service dock-test-k8s -n dev" command to get URL to hit our end point,Which 
   will Start tunnel for service dock-test-k8s and provides us URL 
   - Reference after running command to get URL
   
D:\dockTest\K8s>minikube service dock-test-k8s -n dev
┌───────────┬───────────────┬─────────────┬───────────────────────────┐
│ NAMESPACE │     NAME      │ TARGET PORT │            URL            │
├───────────┼───────────────┼─────────────┼───────────────────────────┤
│ dev       │ dock-test-k8s │ 80          │ http://192.168.49.2:32339 │
└───────────┴───────────────┴─────────────┴───────────────────────────┘
* Starting tunnel for service dock-test-k8s.
  ┌───────────┬───────────────┬─────────────┬────────────────────────┐
  │ NAMESPACE │     NAME      │ TARGET PORT │          URL           │
  ├───────────┼───────────────┼─────────────┼────────────────────────┤
  │ dev       │ dock-test-k8s │             │ http://127.0.0.1:53008 │ // USE THIS URL on browser
  └───────────┴───────────────┴─────────────┴────────────────────────┘

6. So final URL that we have to use will be http://127.0.0.1:53008/welcome

7. We have also made use of configmap.yml to access property files like application.yml - We made use of volumeMounts.
   volumeMounts - "Inject this folder/file inside my container" in mountPath: /config (This is where our application.props will be present with its original values)
8. Rather than applying properties one by one we can make use of application.props to mount to our running docker containers.
   "APP_ENV" is property we used in this demo app.

# Technologies Used

- Java 21
- Spring Boot
- Docker
- Eclipse Temurin OpenJDK 21 image for java
- Minikube


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
mvn clean package

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

Command: docker images

This shows all available Docker images.

REPOSITORY      TAG       IMAGE ID
docktest-app    latest    abc123xyz


# Optional - Step 4: Run Docker Container (Before pushing to docker repo)

Command: docker run -p 8080:8080 docktest-app

Explanation:

| Command Part | Meaning |
|---|---|
| docker run | Starts container |
| -p 8080:8080 | Maps local port to container port |
| docktest-app | Docker image name |

---

# Optional - Step 5: Access Application

Open browser:

```text
http://localhost:8080/welcome
```

Expected Response: Welcome to docker--devTestEnvt--environment

# Docker Container Commands

## View Running Containers

docker ps

## View All Containers
docker ps -a

## Stop Running Container
docker stop <container-id>
Example: docker stop a1b2c3d4


## Start Existing Container

```bash
docker start <container-id>
```

docker rm <container-id>

## Remove Docker Image
docker rmi docktest-app


# Project Flow

Spring Boot Application
        ↓
Build JAR using Maven
        ↓
Create Docker Image
        ↓
Run Docker Container
        ↓
Access Application on Browser

End to End steps followed in this demo k8s deployment for out app:

1. Create Springboot app in spring initializer and create End point to test. (https://start.spring.io/)
2. Create Jar file of our project using cmd : "mvn clean package"
3. Create docker file with name "Dockerfile"
4. Build docker image using cmd - Run this in path where docker file is present : "docker build -t dock-test:v1 ." 
   Image will be built in current directory as we have mentioned fullstop in above cmd.
5. Verify image : "docker images"
6. Optional : If you want to run your image in local : "docker run -p 8080:8080 dock-test:v1:v1"
7. Push your image to docker desktop
   - Create image tag : "docker tag dock-test:v1 vinayakwarad/dock-test:v1"
   - Push docker image : "docker push vinayakwarad/dock-test:v1"
8. This step is to run your image on k8s cluster created by minikube
    - Create deployment.yml , service,yml and configmap.yml as per your requirement
9. Create namespace : "kubectl create namespace dev" and 
    - verify using "kubectl get ns"
    - While working on terminal if you want to set default dev namespace : "kubectl config set-context --current --namespace=dev"
10. Apply Manifest files - deployment, service, configmap - Run from path where these files are present. 
11. Apply deployment yml : "kubectl apply -f deployment.yml"
11. Apply service.yml : "kubectl apply -f service.yml"
12. Apply configmap : "kubectl apply -f configmap.yml"
13. Apply Secret : "kubectl apply -f secret.yml"
4Get URL of where our application can be accessed inside minikube cluster for k8's
    - cmd : "minikube service dock-test-k8s -n dev"
    - Sample output : "http://127.0.0.1:53008" +  append our end point
    - Final URL to be used in browser : http://127.0.0.1:53008/welcome
 
Conclusion : By following these steps, you can seamlessly build a Spring Boot application, containerize it with Docker, 
push it to Docker Hub, and deploy it on a Kubernetes cluster using Minikube—ensuring a complete workflow from local 
development to cloud‑native deployment.

* You can create multiple secret.yml files and apply to K8s cluster (db-secret,camunda-secret,jwt-secret,mail-secret)

# Author
Vinayak Warad