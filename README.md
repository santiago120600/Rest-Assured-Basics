# rest-assured-course

Brief starter project for practicing API testing with Rest-Assured (Maven-based).

## Create the project
Directory must be empty before running the archetype command.

Open PowerShell or CMD in the parent folder and run:

```
mvn archetype:generate -DgroupId=com.restassured \
                       -DartifactId=rest-assured-course \
                       -Dpackage=com.restassured \
                       -DarchetypeArtifactId=maven-archetype-quickstart \
                       -DarchetypeVersion=1.4 \
                       -DinteractiveMode=false
```

Then:
```
cd rest-assured-course
```

To open in VS Code:
```
code .
```

## Podman: build & run

Prerequisites:
- Podman installed and a running Podman machine.

1) Start the Podman machine (PowerShell / CMD):
```
podman machine start podman-machine-default
```

2) Build the image (uses the project Containerfile):
```
podman build -f Containerfile -t rest-assured-course:latest .
```

3) Run the container (the image ENTRYPOINT runs `mvn test`):
```
podman run --rm rest-assured-course:latest
```

Dev container (optional)

To use Podman instead of Docker, configure the Dev Containers extension to use Podman by setting the dockerPath to "podman". See the VS Code docs: https://code.visualstudio.com/remote/advancedcontainers/docker-options#_podman

Install the Dev Containers extension:
- Marketplace: https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers

## Maven

Run a specific test method from a given class:

`mvn test -Dtest=YourTestClass#yourTestMethod`

Example:

`mvn test -Dtest=BasicRestAssuredTest#testGetRequest`

## CURL Examples

### GET request
```
curl -X GET "http://host.docker.internal/api/v1/authors" \
  -H "Accept: application/json"
```

### POST request
```
curl -X POST "http://host.docker.internal/api/v1/authors" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "first_name": "Ernest",
    "last_name": "Hemingway"
  }'
```

### PUT request
```
curl -X PUT "http://host.docker.internal/api/v1/authors/3" \
  -H "Content-Type: application/json" \
  -d '{
    "first_name": "Ernest Miller",
    "last_name": "Hemingway"
  }'
```

### DELETE request
```
curl -X DELETE "http://host.docker.internal/api/v1/authors/4"
```