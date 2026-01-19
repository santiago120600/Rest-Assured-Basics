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

Start the services using Podman Compose:

```
podman compose up --build
```

### VS Code Dev Container Configuration (Optional)

To use Podman instead of Docker with the Dev Containers extension:

1.  Install the [Dev Containers extension](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers).
2.  Configure the extension to use Podman by adding the following setting to your User `settings.json`:

```
{
  "dev.containers.dockerPath": "podman"
}
```

#### Troubleshooting

If you encounter the error:

```
Error: getting absolute path of \\wsl.localhost\Ubuntu\mnt\wslg\runtime-dir\wayl
and-0: unsupported UNC path
```

Add the following to your User `settings.json`:

```
{
  "dev.containers.mountWaylandSocket": false
}
```

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

### Proxy

I will use **mitmproxy**  
Docker Hub: https://hub.docker.com/r/mitmproxy/mitmproxy/

To run mitmweb with Podman:

```
podman run -d \
  --name mitmproxy \
  -p 8866:8866 \
  -p 8081:8081 \
  mitmproxy/mitmproxy:12.1.1 \
  mitmweb --listen-host 0.0.0.0 --listen-port 8866 \
          --web-host 0.0.0.0 --web-port 8081 \
          --set web_password=Password1!
```

The command starts:

- The **proxy** on port **8866**  
- The **web interface** on port **8081**

Test the proxy
`curl -x http://localhost:8866 http://host.docker.internal/api/v1/authors`
