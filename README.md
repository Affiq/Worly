# Worly
<p> An environmental website allowing users to take quizzes and estimate their carbon footprint, implemented as an academic assignment. Implemented using Spring Boot, JPA and Thymeleaf for page rendering. The websites allow users to login as regular users, or administrators. Administrators are able to create new quiz templates, consisting of questions, answers and scores. Users are then able to login and answer these quiz templates to achieve a score. These scores help capture a user's carbon footprint depending on the answers they have chosen, as well as showing the quiz results on how users can better improve their score and hence their carbon footprint. </p> 

<h2> Running the Server </h2>
<p>Locate the file named WorlyApplication.Java, compile it and execute it in a chosen location. For convenience purposes, this demonstration will run the server in the Eclipse Studio workspace. After it runs, we should encounter our first error. This is because our MySQL database is not reachable, and this could be due to a number of reasons, but primarily this is because there is no database set up yet. So we will first need to create a MySQL database.</p>

```
com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure
```

<h2> Controller Overview </h2>
<p> Typically, a Front-End server such as react will handle the page rendering and display, which will send HTTP requests (usually Axiom for React JS) to the Back-End server (Spring Boot). However, due to time constraints, the user will directly interact with the backend which is then responsible for both display and business logic. </p>
