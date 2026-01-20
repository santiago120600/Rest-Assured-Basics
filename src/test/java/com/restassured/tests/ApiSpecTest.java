package com.restassured.tests;

import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class ApiSpecTest {

    /*
     * GET http://host.docker.internal:8081/api/v1/authors HTTP/1.1
     * Accept: application/json
     */

    public static RequestSpecification requestSpecificationExample() {
        return new RequestSpecBuilder()
                .setBaseUri("http://host.docker.internal:8081/api/v1")
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    @Test
    public void testGetRequest() {
        RestAssured.given(requestSpecificationExample())
                .when()
                .get("/authors")
                .then()
                .assertThat()
                .statusCode(200);
    }

    public static ResponseSpecification responseSpecificationExample() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectBody(notNullValue())
                .build();
    }

    @Test
    public void testGetRequest1() {
        RestAssured.given()
                .spec(requestSpecificationExample())
                .when()
                .get("/authors")
                .then()
                .spec(responseSpecificationExample());
    }

    public static RequestSpecification spec;
    private static String baseUrl = "http://host.docker.internal:8081/api/v1";

    @BeforeGroups("specs")
    public void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    @Test(groups = "specs")
    public void testGetRequest2() {
        RestAssured.given()
                .spec(spec)
                .when()
                .get("/authors")
                .then()
                .spec(responseSpecificationExample());
    }
}
