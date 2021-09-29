# StepExecutor
Requirements:

Create a step executor application using Spring Boot, Maven and H2 database. Step represents a task that can be executed. 
For a step to be executed a HTTP Get request needs to be performed by using a URL configured on that step with the following query parameters:
• price - taken from execute step request
• stepName - name of a step being executed
Step is executed successfully If HTTP Get request returns a HTTP 200 response code. Otherwise, we treat a step as a failure.
Example:
• Step definition:
"name": "TestStep",
"url": "https://run.mocky.io/v3/b0487f4b-747b-4b6b-8585-1c5b7120e7b3"
• Execute TestStep with price 10.15 -> https://run.mocky.io/v3/b0487f4b-747b-4b6b-8585-1c5b7120e7b3?price=10.15&stepName=TestStep
After step execution a transaction is created with an appropriate status, price and a timestamp.
Persistence requirements:
• step table - Stores steps that can be executed. Step is uniquely defined by its name. and has a URL that is invoked when executing.
• transaction table - A transaction is created for each step execution. It stores price, timestamp and status of a transaction.
API requirements:
• Endpoint for step creation. Requires name and url as parameters in request body as json. Returns the created step.
Example: Request: curl1 -i -X POST -H 'Content-Type: application/json' -d '{"name": "TestStep", "url":"https://run.mocky.io/v3/989ec045-3272-42c5-8f9e-0632db56ad6b"}' http://localhost:8080/centili/step
Response: HTTP/1.1 200 Content-Type: application/json
Transfer-Encoding: chunked Date: Sun, 29 Aug 2021 19:18:14 GMT
{"id":1,"name":"TestStep","url":"https://run.mocky.io/v3/989ec045-3272-42c5-8f9e-0632db56ad6b"}
Note: Return an appropriate message to caller when trying to create a step with a name that already exists.
1 https://curl.se/
• Endpoint for step execution. Requires step name as path variable and price parameter in request body as json. Returns the created transaction. Example: Request: curl -i -X POST -H 'Content-Type: application/json' -d '{"price": 20.89}' http://localhost:8080/centili/step/TestStep 
Response: HTTP/1.1 200 Content-Type: application/json Transfer-Encoding: chunked Date: Sun, 29 Aug 2021 19:22:18 GMT {"id":2,"stepName":"TestStep","price":20.89,"timestamp":"2021-08-29T21:22:18.666289","status":"SUCCESS"}
Note: Return an appropriate message to caller when trying to execute a step that does not exist.
• Endpoint for transaction report by step name. Requires step name as path variable. Returns all transaction for a given step. 
Example: Request: curl -i -X GET http://localhost:8080/centili/step/TestStep/transactions 
Response: HTTP/1.1 200 Content-Type: application/json Transfer-Encoding: chunked Date: Sun, 29 Aug 2021 19:27:13 GMT
  [{"id":3,"stepName":"TestStep","price":100.00,"timestamp":"2021-08-29T21:26:37.737189","status":"SUCCESS"},{"id":2,"stepName":"TestStep","price":20.89,"timestamp":"2021-08-29T21:22:18.666289","status":"SUCCESS"},{"id":4,"stepName":"TestStep","price":1.55,"timestamp":"2021-08-29T21:26:45.770204","status":"SUCCESS"}] 
Note: Return http status code 404 with empty body if no transactions are found or if a step doesn't exist
For step execution testing purposes the following mock endpoints are available:
• Returns HTTP response code 200 - https://run.mocky.io/v3/989ec045-3272-42c5-8f9e-0632db56ad6b
• Returns HTTP response code 400 - https://run.mocky.io/v3/09922a9a-a0a1-468d-b7a5-2abce1c4d5bf
