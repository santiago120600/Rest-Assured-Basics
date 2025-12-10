package com.restassured.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

public class SchemaValidationTest {

    /*
        GET http://host.docker.internal/api/v1/authors/2 HTTP/1.1
        Accept: application/json
    */

    @Test
    public void testSchema() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.given()
                .baseUri("http://host.docker.internal/api/v1")
                .accept(ContentType.JSON)
                .when()
                .pathParam("id", 2)
                .get("/authors/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/author-success-schema.json"));
    }
}
