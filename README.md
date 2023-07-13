# Worly
<p> An environmental website allowing users to take quizzes and estimate their carbon footprint, implemented as an academic assignment. Implemented using Spring Boot, JPA and Thymeleaf for page rendering. The websites allow users to login as regular users, or administrators. Administrators are able to create new quiz templates, consisting of questions, answers and scores. Users are then able to login and answer these quiz templates to achieve a score. These scores help capture a user's carbon footprint depending on the answers they have chosen, as well as showing the quiz results on how users can better improve their score and hence their carbon footprint. </p> 

<h2> Configuring a MySQL database </h2>
<p>Locate the file named WorlyApplication.Java, compile it and execute it in a chosen location. For convenience purposes, this demonstration will run the server in the Eclipse Studio workspace. After it runs, we should encounter our first error. This is because our MySQL database is not reachable, and this could be due to a number of reasons, but primarily this is because there is no database set up yet. So we will first need to create a MySQL database. </p>

![](https://github.com/Affiq/Worly/blob/main/Images/Springboot1.png)

```
com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure
```

<p> This tutorial will demonstrate how to create a MySQL database using MySQL workbench. Navigate to the workbench and create a new connection The name, user and password can be adjusted accordingly, but the details will need to be remembered as we need to configure our application.properties file to be compatible with our newly created database. After doing so, we will need to create a new Schema (which is a collection of databases /tables) - simply right-click the Schema tab and create a new Schema. For demonstration purposes, we will name this Schema "worlyschema". Note that MySQL only allows lower-case for schema names. After doing so, we now have a new connection to our database, as well as a Schema we can work with.  </p>

![](https://github.com/Affiq/Worly/blob/main/Images/MySQL1.png)

<p> After creating a new MySQL database, we will need to locate the application.properties file located under the resources folder. In here, we can configure some properties of our application. The application.properties file should be configured as the following: </p>

```
## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
spring.datasource.url = [Your Connection Path + Port + Schema]
spring.datasource.username = [User name]
spring.datasource.password = [Password]

## Hibernate Properties
# The SQL dialect for the underlying database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect

# Enabled DDL - The creation of tables
spring.jpa.generate-ddl=true

# Hibernate ddl auto (options: create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update
```

<p> So, making the adjustments for this example should eventually look like: </p> 

```
## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
spring.datasource.url = jdbc:mysql://127.0.0.1:3306/Worly
spring.datasource.username = root
spring.datasource.password = password

## Hibernate Properties
# The SQL dialect for the underlying database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect

# Enabled DDL - The creation of tables
spring.jpa.generate-ddl=true

# Hibernate ddl auto (options: create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update
```

<p> Note the DDL property enables Spring Boot to create databases and tables, rather than entries and records. This is essential upon the first run of the Spring Boot server as it will construct the necessary tables in our database, but should be commented out with a hashtag upon the second run. Sometimes, when the server is run SpringBoot may throw the following error.</p> 

```
Caused by: com.mysql.cj.exceptions.InvalidConnectionAttributeException: The server time zone value 'GMT Summer Time' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the 'serverTimezone' configuration property) to use a more specific time zone value if you want to utilize time zone support.
```

<p> This can be resolved by simply heading back to MySQL workbench, navigating to the appropriate database (using the USE statement) and configuring the global time using the command: </p>

```
USE worlyschema;
SET GLOBAL time_zone = '+3:00';;
```

<p> Finally, the console should return a success message which should look like the following: </p>

```
2023-07-13 20:08:11.748  INFO 8476 --- [  restartedMain] com.example.worly.WorlyApplication       : Started WorlyApplication in 17.927 seconds (JVM running for 19.282)
```

<h2> Controller Sidenote </h2>
<p> Typically, a Front-End server such as react will handle the page rendering and display, which will send HTTP requests (usually Axiom for React JS) to the Back-End server (Spring Boot). However, due to time constraints, the user will directly interact with the backend which is then responsible for both display and business logic. SpringBoot utilises a technology called Thymeleaf and will render the HTML pages we have configured in the Resources folder as a result. </p>
