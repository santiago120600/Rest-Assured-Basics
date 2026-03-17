package com.restassured.tests;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.restassured.models.Author;
import com.restassured.models.Book;
import com.restassured.utils.ExcelUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DataSourceTest {

    private static final String BOOKS_EXCEL = "src/test/resources/Books.xlsx";
    private static final String AUTHORS_EXCEL = "src/test/resources/authors.xlsx";
    private static final String BASE_URL = "http://host.docker.internal:8081/api/v1";

    @DataProvider(name = "authorsData")
    public Object[][] authorsData() throws IOException {
        return ExcelUtils.readSheetAsMatrix(AUTHORS_EXCEL, null);
    }

    @DataProvider(name = "booksData")
    public Object[][] booksData() throws IOException {
        return ExcelUtils.readSheetAsMatrix(BOOKS_EXCEL, null);
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

    @Test(dataProvider = "authorsData")
    public void testPostRequest(String lastName, String firstName) {
        Author authorReq = new Author(firstName, lastName);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(authorReq)
                .when()
                .post("/authors")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .response();

        response.prettyPrint();
    }

    /*
        POST http://host.docker.internal:8081/api/v1/books HTTP/1.1
        content-type: application/json
        Accept: application/json
        
        {
            "title": "The book",
            "isbn": "12345678",
            "aisle_number": 1,
            "author_id": 1
        }
    */

    @Test(dataProvider = "booksData")
    public void testBookPostRequest(String title, String isbn, Double authorId, Double aisle) {
        RestAssured.proxy("host.docker.internal", 8866);
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(Book.builder()
                        .title(title)
                        .isbn(isbn)
                        .authorId(authorId.intValue())
                        .aisle(aisle.intValue())
                        .build())
                .when()
                .post("/books")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .response();
        response.prettyPrint();
    }

}
