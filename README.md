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
