#!/bin/bash

url='http://localhost:8080/lcs'

read -d '' payLoad1 << EOF
	{
        "setOfStrings": [
            {"value": false},
            {"value": "comcastic"},
            {"value": "broadcaster"}
        ]
    }
EOF

read -d '' payLoad2 << EOF
	{
        "setOfStrings": []
    }
EOF


read -d '' payLoad3 << EOF
	{
        "setOfStrings": [
            {"value": "comcast"},
            {"value": "comcast"},
            {"value": "broadcaster"}
        ]
    }
EOF


read -d '' payLoad4 << EOF
	{
        "setOfStrings": [
            {"value": "comcastic"},
            {"value": "comcast"},
            {"value": "broadcaster"}
        ]
    }
EOF


# Test case 1: When no post body in request
echo "Test case 1: When no post body in request"
statusCode=$(curl --write-out %{http_code} --silent --output /dev/null \
-X POST \
-H 'Content-type: application/json' \
--data "" ${url})

if [ $statusCode == 400 ]
then
    echo "PASSED, statuscode= "${statusCode}
else
    echo "FAILED, statuscode= "${statusCode}
fi


# Test case 2: Incorrect POST body format
echo "Test case 2: Incorrect POST body format"
statusCode=$(curl --write-out %{http_code} --silent --output /dev/null \
-X POST \
-H 'Content-type: application/json' \
--data "${payLoad1}" ${url})

if [ $statusCode == 400 ]
then
    echo "PASSED, statuscode= "${statusCode}
else
    echo "FAILED, statuscode= "${statusCode}
fi


# Test case 3: Empty setOfStrings
echo "Test case 3: Empty setOfStrings"
statusCode=$(curl --write-out %{http_code} --silent --output /dev/null \
-X POST \
-H 'Content-type: application/json' \
--data "${payLoad2}" ${url})

if [ $statusCode == 400 ]
then
    echo "PASSED, statuscode= "${statusCode}
else
    echo "FAILED, statuscode= "${statusCode}
fi


# Test case 4: setOfStrings is not a set(unique strings)
echo "Test case 4: setOfStrings is not a set(unique strings)"
statusCode=$(curl --write-out %{http_code} --silent --output /dev/null \
-X POST \
-H 'Content-type: application/json' \
--data "${payLoad3}" ${url})

if [ $statusCode == 400 ]
then
    echo "PASSED, statuscode= "${statusCode}
else
    echo "FAILED, statuscode= "${statusCode}
fi


# Test case 5: Correct post body (Positive test case)
echo "Test case 5: Correct post body (Positive test case)"
statusCode=$(curl \
-X POST \
-i -H "Accept: application/json" -H "Content-Type: application/json" \
--data "${payLoad4}" ${url})

echo "${statusCode}"

RESP=$(echo "${statusCode}" | grep -oP "value")
echo $RESP
