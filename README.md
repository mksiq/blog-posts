## Blog Api

I am currently working, so I had only about a couple of hours daily free to work on this project.

I tried to keep caching simple by using what Spring Boot offers by default. For multi-threading I used CompletableFutures. However, also for simplicity I did not take into account max numbers of threads, and could be improved.
For testing, it is only covering unit test.
Documentation is provided by SpringDocs/Swagger.

Overall, this was a fun project that I enjoyed spending my time on, so thank you.

### For Testing
> mvn test

### For Running
> mvn package
> 
> java -jar ./target/posts-0.0.1-SNAPSHOT.jar

### Sample Query
> curl http://localhost:8080/api/posts?tags=tech,health,science




### Deployed at Heroku
It is deployed on heroku in a free dyno (that probably takes a couple of minutes to wake up):

[Heroku link](https://hatchwaysapi.herokuapp.com/)

### Swagger available at: 

[Swagger Json](localhost:8080/v2/api-docs)


[Swagger UI](http://localhost:8080/swagger-ui)
