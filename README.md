# Longest Common Substring 
This Application is for finding longest common substring from given set of strings. it has three parts, RESTful API, web page and validation script.


### LCS Rest API
This is a java application to find the longest common substrings from given set of strings. it uses java 1.8 and spring-boot framework to build the RESTful API.

##### Build and Run
It uses maven build tool to create artifacts. The maven3+ and jdk1.8 are required to build and deploy this application. To build and run the application commands are listed below.

```
cd lcs-service
mvn clean install
java -jar lcs-service-0.0.1-SNAPSHOT.jar --server.port=8080
```
After executing above commands the rest api will start running on port 8080 and url *http://localhost:8080/lcs*
```
#Sample Curl request
read -d '' payLoad << EOF
	{
        "setOfStrings": [
            {"value": "comcastic"},
            {"value": "comcast"},
            {"value": "broadcaster"}
        ]
    }
EOF
curl -d '{payLoad}' -H 'Content-Type: application/json' http://localhost:8080/lcs
```

### LCS Web Page
This is a html application which renders a form where user can enter above mentioned json object to check the longest common substring. it connects to RESTful API for that purpose.
This page can be deployed in any web server like apache webserver or node server. As it is standalone html page so user can can open this in any web browser.

![image](https://user-images.githubusercontent.com/50758711/130099226-c69cfc42-717b-4c0d-8846-bc74e3effa71.png)
        

        






