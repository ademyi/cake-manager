## Cake Manager Demo 


### Features

- Spring Boot and Spring MVC with Thymeleaf template engine
- CRUD operations 
- Login page
- Role based authorization
- Export to CSV file
- Containarization
- Unit tests

### Clone

    git clone https://github.com/ademyi/cake-manager.git


### Running the application locally

`mvn spring-boot:run`

 or

    mvn clean package
    java -jar target/cake-manager-1.0.0.jar

 After then please go to the link below

    http://localhost:8282 
  
 You will see the login page. 

| username  | password | role |
| ------------- | ------------- |------------- |
| admin  | admin  |Admin, User|
| manager  | manager  |Manager|
| user  | user  |User|

### Running the application locally from Docker

1. building from docker file
 

    sudo docker build -f Dockerfile -t cake-manager .
    sudo docker images`


2. Run your container on port 8282 by the following command;


    docker run -p 8282:8282 cake-manager

3. Go to the browser and then use http://localhost:8282 to login






