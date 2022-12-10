# Spring Cloud Zookeeper

Spring Cloud Zookeeper provides Apache Zookeeper integrations for Spring Boot apps through autoconfiguration and binding to the Spring Environment and other Spring programming model idioms. With a few simple annotations you can quickly enable and configure the common patterns inside your application and build large distributed systems with Zookeeper. The patterns provided include Service Discovery and Distributed Configuration.

## Features

- Service Discovery: instances can be registered with Zookeeper and clients can discover the instances using Spring-managed beans

- Supports Spring Cloud LoadBalancer - client-side load-balancing solution

- Supports Spring Cloud OpenFeign

- Distributed Configuration: using Zookeeper as a data store

## Setup

### pom.xml

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
</dependencies>
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### application.yml

```yaml
spring:
  application:
    name: spring-zookeeper
  cloud:
    zookeeper:
      connect-string: localhost:2181
```

## Start the Zookeeper server

Run this command to start all services in the correct order.

```bash
$ docker-compose up -d
```

## View the Zookeeper UI

Navigate to [http://localhost:8080/commands](http://localhost:8080/commands) on your browser to access the Zookeeper UI.

List of available commands:

- configuration
- connection_stat_reset
- connections
- dirs
- dump
- environment
- get_trace_mask
- hash
- initial_configuration
- is_read_only
- last_snapshot
- leader
- monitor
- observer_connection_stat_reset
- observers
- ruok
- server_stats
- set_trace_mask
- stat_reset
- stats
- system_properties
- voting_view
- watch_summary
- watches
- watches_by_path
- zabstate

## Run the application

Run this command to start two instances of the application. 

```
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=9090
```

```
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=9091
```

### REST endpoints

| HTTP verb | Resource  | Description
|----|---|---|
|  GET  | /persons  | retrieve list and information of persons  
|  GET |  /persons/{id} | retrieve information of a person specified by {id}
|  POST | /persons  | create a new person with payload  
|  PUT   |  /persons/{id} | update a person with payload   
|  DELETE   | /persons/{id}  |  delete a person specified by {id} 
|  GET  | /countries  | retrieve list and information of countries  
|  GET |  /countries/{name} | retrieve information of a country specified by {name} 
|  POST | /countries  | create a new country with payload  
|  PUT   |  /countries/{name} | update a country with payload   
|  DELETE   | /countries/{name}  |  delete a country specified by {name} 


## Stop the Zookeeper server

```bash
docker-compose down
```
