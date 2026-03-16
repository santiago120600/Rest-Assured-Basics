package com.restassured.tests;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicRestAssuredTest {

    /*
        GET http://host.docker.internal:8081/api/v1/authors HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testGetRequest() {
        RestAssured.basePath = "/authors";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Response response = RestAssured.given()
                .baseUri("http://host.docker.internal:8081/api/v1")
                .accept(ContentType.JSON)
                .when()
                .get();

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.getBody().asString());
    }

    /*
        GET http://host.docker.internal:8081/api/v1/authors HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testGetRequest1() {
        Response response = RestAssured.given()
            .baseUri("http://host.docker.internal:8081/api/v1")
            .basePath("/authors")
            // .filters(new RequestLoggingFilter(), new ResponseLoggingFilter())
            .accept(ContentType.JSON)
            .when()
            .get()
            .then()
            .assertThat()
            .statusCode(200)
            .body(notNullValue())
            .extract().response();
        // Print the response body
        response.prettyPrint();
    }

    /*
        GET http://host.docker.internal:8081/api/v1/books HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testGetRequestBook() {
        RestAssured.given()
            .baseUri("http://host.docker.internal:8081/api/v1")
            .basePath("/books")
            .filters(new RequestLoggingFilter(), new ResponseLoggingFilter())
            .accept(ContentType.JSON)
            .when()
            .get()
            .then()
            .assertThat()
            .statusCode(200)
            .body(notNullValue())
            .extract().response();
    }

    /*
        GET http://host.docker.internal:8081/api/v1/authors?firstName=Ernest HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testGetQueryParamsRequest() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Response response = RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .queryParam("firstName", "Ernest")
            .get("http://host.docker.internal:8081/api/v1/authors")
            .then()
            .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

      /*
        GET http://host.docker.internal:8081/api/v1/books?isbn=9780307409512 HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testGetQueryParamsRequestBook() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Response response = RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .queryParam("isbn", "9780307409512")
            .get("http://host.docker.internal:8081/api/v1/books")
            .then()
            .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    /*
        GET http://host.docker.internal:8081/api/v1/authors/3 HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testGetPathParamsRequest() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Response response = RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .pathParam("id", 3)
            .get("http://host.docker.internal:8081/api/v1/authors/{id}")
            .then()
            .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    /*
        GET http://host.docker.internal:8081/api/v1/books/3 HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testGetPathParamsRequestBook() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Response response = RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .pathParam("id", 1)
            .get("http://host.docker.internal:8081/api/v1/books/{id}")
            .then()
            .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    /*
        POST http://host.docker.internal:8081/api/v1/authors HTTP/1.1
        content-type: application/json
        Accept: application/json

        {
            "first_name": "Ernest",
            "last_name": "Hemingway"
        }
    */

    @Test
    public void testPostRequest() {
        String requestBody = "{\"first_name\": \"Ernest\", \"last_name\": \"Hemingway\"}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://host.docker.internal:8081/api/v1/authors");

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(response.getBody().asString());
        // Print the response body
        response.prettyPrint();
    }

    /*
        POST http://host.docker.internal:8081/api/v1/books HTTP/1.1
        content-type: application/json
        Accept: application/json

        {
            "title": "The Old Man and the Sea",
            "isbn": "9780307409512",
            "aisle_number": 1,
            "author_id": 3
        }
    */

    @Test
    public void testPostRequestBook() {
        String requestBody = "{\"title\": \"The Old Man and the Sea\", \"isbn\": \"9780307409512\", \"aisle_number\": 1, \"author_id\": 3}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://host.docker.internal:8081/api/v1/books");

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(response.getBody().asString());
        // Print the response body
        response.prettyPrint();
    }

    /*
        PUT http://host.docker.internal:8081/api/v1/authors/3 HTTP/1.1
        content-type: application/json

        {
            "first_name": "Ernest Miller",
            "last_name": "Hemingway"
        }
    */

    @Test
    public void testPutRequest() {
        String requestBody = "{\"first_name\": \"Ernest Miller\", \"last_name\": \"Hemingway\"}";

        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter())
                .pathParam("id", 3)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("http://host.docker.internal:8081/api/v1/authors/{id}");

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.getBody().asString());
        // Print the response body
        response.prettyPrint();
    }

    /*
        PUT http://host.docker.internal:8081/api/v1/books/3 HTTP/1.1
        content-type: application/json

        {
            "title": "The Old Man and the Sea",
            "isbn": "9780307409512",
            "aisle_number": 4,
            "author_id": 3
        }
    */

    @Test
    public void testPutRequestBook() {
        String requestBody = "{\"title\": \"The Old Man and the Sea\", \"isbn\": \"9780307409512\", \"aisle_number\": 4, \"author_id\": 3}";

        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter())
                .pathParam("id", 1)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("http://host.docker.internal:8081/api/v1/books/{id}");

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.getBody().asString());
        // Print the response body
        response.prettyPrint();
    }

    /*
        DELETE http://host.docker.internal:8081/api/v1/authors/4
    */

    @Test
    public void testDeleteRequest() {

        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter())
                .baseUri("http://host.docker.internal:8081/api/v1")
                .basePath("/authors/{id}")
                .pathParam("id", 4)
                .when()
                .delete();

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 200);
        // Print the response body
        response.prettyPrint();
    }

     /*
        DELETE http://host.docker.internal:8081/api/v1/books/1
    */

    @Test
    public void testDeleteRequestBook() {

        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter())
                .baseUri("http://host.docker.internal:8081/api/v1")
                .basePath("/books/{id}")
                .pathParam("id", 1)
                .when()
                .delete();

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 200);
        // Print the response body
        response.prettyPrint();
    }

    @Test
    public void testJsonPathExtract() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        String firstName = RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .pathParam("id", 2)
            .get("http://host.docker.internal:8081/api/v1/authors/{id}")
            .then()
            .statusCode(200)
            .extract()
            .path("first_name");

        Assert.assertEquals(firstName, "Ernest");
    }

    @Test
    public void testJsonPathExtractBook() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        String title = RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .pathParam("id", 2)
            .get("http://host.docker.internal:8081/api/v1/books/{id}")
            .then()
            .statusCode(200)
            .extract()
            .path("title");

        Assert.assertEquals(title, "the book");
    }

    @Test
    public void testSpecificFields() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .pathParam("id", 2)
            .get("http://host.docker.internal:8081/api/v1/authors/{id}")
            .then()
            .statusCode(200)
            .body("first_name", equalTo("Ernest"))
            .body("last_name", equalTo("Hemingway"));
    }

    @Test
    public void testSpecificFieldsBook() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .pathParam("id", 2)
            .get("http://host.docker.internal:8081/api/v1/books/{id}")
            .then()
            .statusCode(200)
            .body("title", equalTo("the book"))
            .body("isbn", equalTo("12345678"));
    }

    @Test
    public void testMultipleFields() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .pathParam("id", 2)
            .get("http://host.docker.internal:8081/api/v1/authors/{id}")
            .then()
            .statusCode(200)
            .body("first_name", equalTo("Ernest"),
                  "last_name", equalTo("Hemingway"));
    }

    /*
        GET http://host.docker.internal:8081/api/v1/books/1 HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testNestedField() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .pathParam("id", 1)
            .get("http://host.docker.internal:8081/api/v1/books/{id}")
            .then()
            .statusCode(200)
            .body("author.last_name", equalTo("Hemingway"));
    }

    /*
        GET http://host.docker.internal:8081/api/v1/authors HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testArrayFields() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .get("http://host.docker.internal:8081/api/v1/authors")
            .then()
            .statusCode(200)
            .body("", hasSize(5))
            .body("id", hasItems(1,2,3,5,6));
    }


}