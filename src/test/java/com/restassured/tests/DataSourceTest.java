package com.restassured.tests;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.restassured.models.Author;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DataSourceTest {

    private static final String EXCEL_FILE_PATH = "src/test/resources/authors.xlsx";
    private static final String BASE_URL = "http://host.docker.internal/api/v1";

    @DataProvider(name = "dataFromExcel")
    public Object[][] getDataFromExcel() throws IOException {
        try (FileInputStream file = new FileInputStream(EXCEL_FILE_PATH);
                XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheetAt(0);

            // Get row count: lastRowNum gives last row index, subtract 1 for header
            int rowCount = sheet.getLastRowNum();

            // Get column count from first data row
            Row firstDataRow = sheet.getRow(1);
            int colCount = firstDataRow != null ? firstDataRow.getLastCellNum() : 0;

            // Initialize data array
            Object[][] testData = new Object[rowCount][colCount];

            // Iterate through rows (skip header at index 0)
            int dataIndex = 0;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null) {
                    continue; // Skip empty rows
                }

                // Iterate through cells using explicit loop to handle sparse rows
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);

                    if (cell == null) {
                        testData[dataIndex][j] = "";
                        continue;
                    }

                    // Extract cell value based on cell type
                    switch (cell.getCellType()) {
                        case STRING:
                            testData[dataIndex][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            // Check if numeric value is actually an integer
                            double numValue = cell.getNumericCellValue();
                            testData[dataIndex][j] = (numValue == (int) numValue)
                                    ? (int) numValue
                                    : numValue;
                            break;
                        case BOOLEAN:
                            testData[dataIndex][j] = cell.getBooleanCellValue();
                            break;
                        case BLANK:
                            testData[dataIndex][j] = "";
                            break;
                        default:
                            testData[dataIndex][j] = cell.toString();
                    }
                }
                dataIndex++;
            }

            return testData;
        }
    }

    /*
        POST http://host.docker.internal/api/v1/authors HTTP/1.1
        content-type: application/json
        Accept: application/json
        
        {
        "first_name": "Ernest",
        "last_name": "Hemingway"
        }
    */

    @Test(dataProvider = "dataFromExcel")
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

        System.out.println("Response Status: " + response.getStatusCode());
        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 201,
                "Expected 201 Created status code");
    }

}
