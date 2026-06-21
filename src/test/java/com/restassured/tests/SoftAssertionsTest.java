package com.restassured.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

public class SoftAssertionsTest {

    /*
        GET http://host.docker.internal:8081/api/v1/books/3 HTTP/1.1
        Accept: application/json
    */

    @Test
    public void softAssertionsTest() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Response response = RestAssured.given()
                .when()
                .pathParam("id", 3)
                .get("http://host.docker.internal:8081/api/v1/books/{id}");
        
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(response.jsonPath().getInt("id"), "Book ID is null");
        softAssert.assertEquals(response.jsonPath().getString("title"), "The Catcher in the Rye", "Title mismatch");
        softAssert.assertEquals(response.jsonPath().getString("isbn"), "9780307409513", "ISBN mismatch");
        softAssert.assertNotNull(response.jsonPath().getInt("author.id"), "Author ID is null");
        softAssert.assertEquals(response.jsonPath().getString("author.first_name"), "Mario", "Author First Name mismatch");
        softAssert.assertEquals(response.jsonPath().getString("author.last_name"), "Pacheco", "Author Last Name mismatch");
        // Report all failures at the end
        softAssert.assertAll();
    }

    /*
        GET http://host.docker.internal:8081/api/v1/books/3 HTTP/1.1
        Accept: application/json
    */

    @Test
    public void hardAssertionsTest() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Response response = RestAssured.given()
                .when()
                .pathParam("id", 3)
                .get("http://host.docker.internal:8081/api/v1/books/{id}");
        
        Assert.assertNotNull(response.jsonPath().getInt("id"), "Book ID is null");
        Assert.assertEquals(response.jsonPath().getString("title"), "The Catcher in the Rye", "Title mismatch");
        Assert.assertEquals(response.jsonPath().getString("isbn"), "97803074095139", "ISBN mismatch");
        Assert.assertNotNull(response.jsonPath().getInt("author.id"), "Author ID is null");
        Assert.assertEquals(response.jsonPath().getString("author.first_name"), "Mario", "Author First Name mismatch");
        Assert.assertEquals(response.jsonPath().getString("author.last_name"), "Pacheco", "Author Last Name mismatch");
    }
}
