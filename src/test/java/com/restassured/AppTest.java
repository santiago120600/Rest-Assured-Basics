package com.restassured;

import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AppTest 
{
    /*  
        GET https://catfact.ninja/fact
    */

    @Test
    public void getFacts()
    {
        RestAssured.baseURI = "https://catfact.ninja/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .when()
            .get("/fact")
            .then()
            .statusCode(200)
            .extract().response();
    }
}
