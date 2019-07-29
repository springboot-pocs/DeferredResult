# DeferredResult
This is a Spring Boot project being used to explore Deferred Result in spring boot

# URL to test:
  http://localhost:6060/dfr/getResults   ( this is a GET request ) 
  

# Behaviour
In the implementation what we are observing is :
The endpoint is returning a DeferredResult object 
We spawn a Async Thread pool - to which spring will defer the request processing 
This will ensure that app server threads are freed up and available to serve further incoming requests.

NOTE : client is not freed up - but has to wait for the request to be processed.
This has been proven by sleeping our processing for X seconds.

As a result Postman ( client ) also waits for X+ seconds for response.

