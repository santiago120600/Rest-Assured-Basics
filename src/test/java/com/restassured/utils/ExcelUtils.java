package com.restassured.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    public static Object[][] readSheetAsMatrix(String filePath, String sheetName) throws IOException {
        try (FileInputStream file = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = (sheetName != null)
                    ? workbook.getSheet(sheetName)
                    : workbook.getSheetAt(0);

            int rowCount = sheet.getLastRowNum(); // excludes header
            Row firstDataRow = sheet.getRow(1);
            int colCount = firstDataRow != null ? firstDataRow.getLastCellNum() : 0;

            Object[][] testData = new Object[rowCount][colCount];

            int dataIndex = 0;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header row 0
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);

                    if (cell == null) {
                        testData[dataIndex][j] = "";
                        continue;
                    }

                    switch (cell.getCellType()) {
                        case STRING:
                            testData[dataIndex][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
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
}