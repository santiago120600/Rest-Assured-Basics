# Rest-Assured Course

A starter project for learning and practicing REST API testing with **Rest-Assured**, **TestNG**, **Maven**, and **Podman**.

## Features

- REST API testing with Rest-Assured
- TestNG test suite
- JSON Schema validation
- Service virtualization with Mountebank
- HTTP traffic inspection using mitmproxy
- VS Code Dev Container support
- Podman-based development environment

---

# Project Setup

## Create the project

Start from an **empty directory** and run the Maven archetype:

```bash
mvn archetype:generate \
  -DgroupId=com.restassured \
  -DartifactId=rest-assured-course \
  -Dpackage=com.restassured \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.4 \
  -DinteractiveMode=false
````

Change into the project directory:

```bash
cd rest-assured-course
```

Open the project in VS Code:

```bash
code .
```

---

# Running the Project

## Prerequisites

* Java
* Maven
* Podman
* Podman Machine (running)

Start all services:

```bash
podman compose up --build
```

---

# VS Code Dev Container (Optional)

If you want to use VS Code Dev Containers with **Podman** instead of Docker:

1. Install the **Dev Containers** extension.
2. Add the following to your **User** `settings.json`:

```json
{
  "dev.containers.dockerPath": "podman"
}
```

## Troubleshooting

If you receive:

```text
Error: getting absolute path of \\wsl.localhost\Ubuntu\mnt\wslg\runtime-dir\wayland-0: unsupported UNC path
```

Disable Wayland socket mounting:

```json
{
  "dev.containers.mountWaylandSocket": false
}
```

---

# Running Tests

Run all tests:

```bash
mvn test
```

Run a specific test class:

```bash
mvn test -Dtest=BasicRestAssuredTest
```

Run a specific test method:

```bash
mvn test -Dtest=BasicRestAssuredTest#testGetRequest
```

Example:

```bash
mvn test -Dtest=DataSourceTest#testBookPostRequest
```

Run a TestNG suite:

```bash
mvn test -DsuiteXmlFiles=testng.xml
```

---

# cURL Examples

## GET

```bash
curl -X GET "http://host.docker.internal/api/v1/authors" \
  -H "Accept: application/json"
```

## POST

```bash
curl -X POST "http://host.docker.internal/api/v1/authors" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "first_name": "Ernest",
    "last_name": "Hemingway"
  }'
```

## PUT

```bash
curl -X PUT "http://host.docker.internal/api/v1/authors/3" \
  -H "Content-Type: application/json" \
  -d '{
    "first_name": "Ernest Miller",
    "last_name": "Hemingway"
  }'
```

## DELETE

```bash
curl -X DELETE "http://host.docker.internal/api/v1/authors/4"
```

---

# HTTP Proxy (mitmproxy)

Run **mitmweb** using Podman:

```bash
podman run -d \
  --name mitmproxy \
  -p 8866:8866 \
  -p 8082:8081 \
  mitmproxy/mitmproxy:12.1.1 \
  mitmweb \
    --listen-host 0.0.0.0 \
    --listen-port 8866 \
    --web-host 0.0.0.0 \
    --web-port 8081 \
    --set web_password=Password1!
```

This starts:

| Service | Port |
| ------- | ---- |
| Proxy   | 8866 |
| Web UI  | 8082 |

Test the proxy:

```bash
curl -x http://localhost:8866 http://host.docker.internal/api/v1/authors
```

---

# Service Virtualization (Mountebank)

Start the virtualization server:

```bash
docker run -d \
  --name mountebank \
  -p 2525:2525 \
  -p 5555:5555 \
  -v ./virtualServer/imposter.json:/imposter.json \
  -v ./virtualServer/replace_string.js:/replace_string.js \
  andyrbell/mountebank:2.7.0 \
  mb --configfile /imposter.json --allowInjection
```

---

# Mock Endpoints

## Books

| Method | Endpoint                               |
| ------ | -------------------------------------- |
| GET    | `http://localhost:5555/api/v1/books`   |
| GET    | `http://localhost:5555/api/v1/books/5` |
| POST   | `http://localhost:5555/api/v1/books`   |
| PUT    | `http://localhost:5555/api/v1/books/4` |
| DELETE | `http://localhost:5555/api/v1/books/1` |

### Sample Book Payload

```json
{
  "title": "The Old Man and the Sea",
  "aisle_number": 1,
  "isbn": "9781476787855",
  "author_id": 2
}
```

---

## Authors

| Method | Endpoint                                 |
| ------ | ---------------------------------------- |
| GET    | `http://localhost:5555/api/v1/authors/1` |
| POST   | `http://localhost:5555/api/v1/authors`   |
| PUT    | `http://localhost:5555/api/v1/authors/4` |
| DELETE | `http://localhost:5555/api/v1/authors/1` |

### Sample Author Payload

```json
{
  "first_name": "Rossie",
  "last_name": "Bode"
}
```

---

# Useful References

* JSON Schema: https://json-schema.org/learn/getting-started-step-by-step
* Maven Surefire Plugin: https://maven.apache.org/surefire/maven-surefire-plugin/examples/testng.html
* ExtentReports: https://extentreports.com/docs/versions/5/java/index.html
* TestNG XML: https://testng.org/#_testng_xml