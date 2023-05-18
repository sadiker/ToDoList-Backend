# ToDoList-Backend
# Technologies :  Spring Boot, MongoDB , Spring Data MongoDB , Maven , Swagger, Docker , JUnit / Mockito , Spring Cache , Spring Validation , Lombok , Dev Tools 
## Build,Test,Run the App (Options)
### 1) With Docker Containers    
 1- Download the repository as zip (You can use above "Code" button on GitHub)
  	
 2- Navigate to main proje root on terminal 
  	
	cd / ToDoList-Backend
3- For Maven clean and install and test the project (you can see test results)
  	
	mvn clean | mvn install
	mvn test 
 
4-  Pull mongo image from DockerHub 
  
	docker pull mongo:latest
5- Run mongo image  
  	
	docker run -d -p 27017:27017 --name mobisemmongodb mongo:latest
6- Dockerize the application  
  	
	docker build -t todoimage .
7- Run the image and link that mobisemmonggodb container 
  	
	docker run -p 8080:8080 --name todocontainer --link mobisemmongodb:mongo -d todoimage
8- You can control the running containers 
  	
	docker ps  || docker logs todocontainer
9- You can write the browser and see Swagger api documantion
  	
	http://localhost:8080/swagger-ui/index.html 
### 2) With Docker Containers - Image From DockerHub   	
1- For pull the app image  
  	
	docker pull sadiker/mobisem:latest
2- For pull the mongo image 
  	
	docker pull mongo:latest
 
3- Run mongo image   
  
	docker run -d -p 27017:27017 --name mobisemmongodb mongo:latest
4- Run app image link that mongo image container  
  	
	docker run -p 8080:8080 --name todocontainer --link mobisemmongodb:mongo -d sadiker/mobisem
  
5- You can control the running containers 
  	
	docker ps  || docker logs todocontainer
6- You can write the browser and see Swagger api documantion
  	
	http://localhost:8080/swagger-ui/index.html 
	 

### 3) On Local   
 1- Download the repository as zip or copy from link :
  	
	git clone https://github.com/sadiker/ToDoList-Backend.git
 2- Navigate to main proje root on terminal 
  	
	cd / ToDoList-Backend
3- For Maven clean and install 
  	
	mvn clean 	
	mvn install
4- Navigate to target folder,Run the project jar file  (Before this, you must write "localhost" instead of "mobisemmongodb" on the "application.properties" file) 
  
	cd target	
	java -jar springboot-mongo-docker.jar
5- For unittest , navigate to test folder 
  	
	cd src | cd test 	
	mvn test 
