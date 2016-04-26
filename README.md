[![Circle CI](https://circleci.com/gh/boris-org/kinneret-server/tree/master.svg?style=svg)](https://circleci.com/gh/boris-org/kinneret-server/tree/master)
[![Travis CI](https://travis-ci.org/boris-org/kinneret-server.svg?branch=master)](https://travis-ci.org/boris-org/kinneret-server)
[![codecov.io](https://codecov.io/gh/boris-org/kinneret-server/coverage.svg?branch=master)](https://codecov.io/github/boris-org/kinneret-server?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/d6a24d6b4aef48fd8d725e6762b627a6)](https://www.codacy.com/app/boriska70/kinneret-server)

# Preface

Server demo implementation for the devops course.
It is based on Dropwizard and Maven
Unit and System tests are written with Junit and use Dropwizard handy classes.
Modified!

# Build And Run
* clone the repo
* To Build and Test the service: run "mvn clean install"
* To run the service: run "java -jar target\kinneret-server-1.0-SNAPSHOT.jar server kinneret-server.yml"
* You can find more detailed information [here](https://dropwizard.github.io/dropwizard/0.6.2/getting-started.html#building-fat-jars) about how to build and run Dropwizard application

# Server's API

Supported REST endpoints:

```
 DELETE  /tasks/{id} 
 GET     /tasks
 GET     /tasks/{id}
 POST    /tasks
```
    
To create new task POST on ```http://localhost:9000/tasks``` with json body looks like this:
```javascript
{"description":"blibli"}
```
will create new task, The response will contain the same task with the id generated by the server
(be aware to add the Content-Type: application/json to the http request)


To get all tasks GET on ```http://localhost:9000/tasks```, returns list of tasks formated in json, for example:
```javascript
[{"id":10,"description":"blibli"},{"id":2,"description":"blabla2"},{"id":4,"description":"blabla2"},{"id":5,"description":"blabladrggdfdfgdf"},{"id":6,"description":"kuku"},{"id":7,"description":"blabladfdfd"},{"id":8,"description":"kuku"},{"id":9,"description":"blabla2"}]
```

To delete a task DELETE on ```http://localhost:9000/tasks/{id}``` {id} should be the task id you would like to delete

You can also get single task using GET on ```http://localhost:9000/tasks/{id}```
