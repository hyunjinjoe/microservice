# Deploy Spring Boot Project on Docker

### 1. bootstrap class create & pom.xml update
- bootstrap class
```java
package com.msa.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroserviceApplication.class, args);
	}
        ....
        ....
}
```
- controller class
```java
package com.msa.spring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	@RequestMapping("/")
	public String home() {
		return "Hello, Spring Boot!!";
	}
}
```
- add docker image prefix & docker-maven-plugin of pom.xml
```xml
	<properties>
		<docker.image.prefix>hyunjinjoe</docker.image.prefix>	// docker account	
	</properties>
        ....
        ....
	  <plugin>
	     <groupId>com.spotify</groupId>
	     <artifactId>docker-maven-plugin</artifactId>
	     <version>1.1.1</version>
	     <configuration>
		<imageName>${docker.image.prefix}/${project.artifactId}</imageName>
		<dockerDirectory>src/main/docker</dockerDirectory> // location of Dockerfile
		<resources>
		   <resource>
		      <targetPath>/</targetPath>
		      <directory>${project.build.directory}</directory>
		      <include>${project.build.finalName}.jar</include>
		   </resource>
		</resources>
	     </configuration>
	    </plugin>			
```

### 2. Make Dockerfile
Location of Dockerfile is src/main/docker (filename : Dockerfile )
```bash
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD microservice-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
```

### 3. Create Docker image
```bash
mvn package docker:build -DpushImageTag -DdockerImageTags=v1
```

### 4. Execute Docker image
execute docker image with docker-compose

- microservice.yml
```xml
    version: '3.2'
      services:
	microservice:
	  image: hyunjinjoe/microservice:v1
	  ports:
	    - "8080:8080"

```
```xml
docker-compose -f microservice.yml up
```

### 5. Push Docker image at Repository
push docker image at the docker hub ( need to docker account )
```bash
docker push hyunjinjoe/microservice:v1
```

