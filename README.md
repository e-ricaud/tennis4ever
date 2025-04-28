# Tennis 4 Ever

At the beggining, this application is a propposal of KATA.
There are one utility at this time, get the score detail of an sequence of winning balls.

Tennis 4 Ever - api
=================================
The API webapp for Tennis 4 Ever is the only way to get score detail. (example : curl http://localhost:8080/api/scoring/scoringDetail?scoringInput=AAAA)

Build it
--------

Running tests:
```
mvn clean install
```

Just build the artifact
```
mvn clean install -DskipTests
```

Run it
------
With a maven plugin
```
mvn spring-boot:run
```

You can then access it on `localhost:8080/api`

REST API Documentation
----------------------

http://localhost:8080/swagger-ui/index.html

Tests
-----

Unit tests
```
mvn clean test
```
