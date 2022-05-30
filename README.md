## Simple URL shortening Service

The service should be implemented as stand-alone Spring Boot 
application and provides following features as RESTful API:

- Shortening: Take a URL and return a much shorter URL.
- Redirection: Take a short URL and redirect to the original URL.
- Custom URL: Allow the users to pick custom shortened URL.
- Analytics: How many people clicked the shortened URL in the last day?

##How to start the application

The easiest way to run the application is to open project in IntelliJ Idea, open
`Application.java` class and click `Run 'Application.main()'` button

Another way to start is to run maven command
`mvnw spring-boot:run`

##How to test the application
you can use these curl commands:

- Create new short url: `curl -X POST localhost:8080 -H "Content-Type:application/json" -d '{"originalUrl":"https://example.com"}'`
wil return `{"hash":"aaaaaa","originalUrl":"https://example.com"}`
- Use the created short url: just paste in the browser `localhost:8080/aaaaaa` and it will redirect to original url
or do the `curl localhost:8080/aaaaaa`
- Create new short url with custom hash: `curl -X POST localhost:8080 -H "Content-Type:application/json" -d '{"originalUrl":"https://example.com", "hash": "exmpl"}'`
  wil return `{"hash":"exmpl","originalUrl":"https://example.com"}`
- Statistics: `curl localhost:8080/aaaaaa/statistics` will return `{"hash":"aaaaaa","dayClicks":0}`
