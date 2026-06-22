# Mountebank Virtual Server

This project uses **Mountebank** to mock REST APIs for testing.

## Start Mountebank

Run the following command to start a Mountebank container with the predefined imposter configuration:

```bash
podman run -d \
  --name mountebank \
  -p 2525:2525 \
  -p 5555:5555 \
  -v ./virtualServer/imposter.json:/imposter.json \
  andyrbell/mountebank:2.7.0 \
  mb --configfile /imposter.json
```

Once the container is running:

* **Mountebank Admin API:** http://localhost:2525
* **Mock API:** http://localhost:5555

## Run locally

To run Mountebank without a container, install it globally with npm:

```bash
npm install -g mountebank
```

Then start Mountebank with the config file:

```bash
mb --configfile ./virtualServer/imposter.json
```

## Notes

- Mountebank’s default admin interface runs on port `2525`.
- The mock service port `5555` is defined by the imposter configuration file.
- After starting Mountebank locally, you can also access its built-in documentation from the admin URL.

---

# Manage Imposters

## Upload an Imposter

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d "@virtualServer/imposter.json" \
  http://localhost:2525/imposters
```

Equivalent HTTP request:

```http
POST http://localhost:2525/imposters HTTP/1.1
Content-Type: application/json

< ./virtualServer/imposter.json
```

## Delete an Imposter

```bash
curl -X DELETE http://localhost:2525/imposters/5555
```

Equivalent HTTP request:

```http
DELETE http://localhost:2525/imposters/5555
```

## List All Imposters

```bash
curl http://localhost:2525/imposters
```

Equivalent HTTP request:

```http
GET http://localhost:2525/imposters
```

## View Requests Received by an Imposter

```bash
curl http://localhost:2525/imposters/5555
```

Equivalent HTTP request:

```http
GET http://localhost:2525/imposters/5555
```

---

# Useful Endpoints

| Endpoint                             | Description                                         |
| ------------------------------------ | --------------------------------------------------- |
| http://localhost:2525/logs           | View Mountebank logs                                |
| http://localhost:2525/imposters/5555 | View the imposter configuration and request history |
| http://localhost:5555                | Mock API base URL                                   |

---

# Mock API

## Books

### Get all books

```http
GET http://localhost:5555/api/v1/books
```

### Get a book by ID

```http
GET http://localhost:5555/api/v1/books/1
```

### Create a book

```http
POST http://localhost:5555/api/v1/books
Content-Type: application/json

{
  "title": "The Old Man and the Sea",
  "aisle_number": 1,
  "isbn": "9781476787855",
  "author_id": 2
}
```

### Update a book

```http
PUT http://localhost:5555/api/v1/books/1
Content-Type: application/json

{
  "title": "The Old Man and the Sea",
  "aisle_number": 3,
  "isbn": "9781476787857",
  "author_id": 2
}
```

### Delete a book

```http
DELETE http://localhost:5555/api/v1/books/1
```

---

## Authors

### Get all authors
```http
GET http://localhost:5555/api/v1/authors
```


### Get an author

```http
GET http://localhost:5555/api/v1/authors/1
```

### Create an author

```http
POST http://localhost:5555/api/v1/authors
Content-Type: application/json

{
  "first_name": "Rossie",
  "last_name": "Bode"
}
```

### Update an author

```http
PUT http://localhost:5555/api/v1/authors/4
Content-Type: application/json

{
  "first_name": "Rossie",
  "last_name": "Bode"
}
```

### Delete an author

```http
DELETE http://localhost:5555/api/v1/authors/1
```
