package com.restassured.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.restassured.models.Author;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class POJOsTest {

    String baseUri = "http://host.docker.internal/api/v1";
    public static RequestSpecification spec;

    @BeforeTest
    public void setup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        spec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .build();
    }

    /*
        GET http://host.docker.internal/api/v1/authors/2 HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testDeserialization() {

        Response response = given(spec)
                .when()
                .pathParam("id", 2)
                .get("/authors/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        Author author = response.getBody().as(Author.class);
        Assert.assertNotNull(author.getId());
        Assert.assertNotNull(author.getFirstName());
        Assert.assertNotNull(author.getLastName());
    }

    /*
        POST http://host.docker.internal/api/v1/authors HTTP/1.1
        content-type: application/json
        Accept: application/json
        
        {
        "first_name": "Emilio",
        "last_name": "Pacheco"
        }
    */

    @Test
    public void testSerialization() {
        Author authorReq = new Author("Emilio", "Pacheco");

        Response response = given(spec)
                .body(authorReq)
                .when()
                .post("/authors")
                .then()
                .statusCode(201)
                .extract().response();

        Author authorResp = response.getBody().as(Author.class);
        Assert.assertEquals(authorResp.getFirstName(), authorReq.getFirstName());
        Assert.assertEquals(authorResp.getLastName(), authorReq.getLastName());
    }
}
