package com.example.utils;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class ReadExcelData {

    public static List<List<List<Object>>> dataTable;
    public static Map<String,List<List<Object>>> dataTableWithSheetName;

    public static List<List<List<Object>>> readDataFromExcel(String filename) throws IOException, InvalidFormatException {
        String excelPath = Paths.get("src/test/resources/excelData/" + filename + ".xlsx").toAbsolutePath().toString();
        System.out.println("Reading Excel from : " + excelPath);
        XSSFWorkbook workbook = new XSSFWorkbook(new File(excelPath));
        Iterator<Sheet> sheetIterator = workbook.iterator();
        dataTable = new ArrayList<>();
        while (sheetIterator.hasNext()) {
            List<List<Object>> sheet = new ArrayList<>();
            Iterator<Row> rowIterator = sheetIterator.next().rowIterator();
            while (rowIterator.hasNext()) {
                List<Object> row = new ArrayList<>();
                Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            row.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            row.add(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            row.add(cell.getBooleanCellValue());
                            break;
                        case BLANK:
                            row.add(null);
                            break;
                    }
                }
                sheet.add(row);
            }
            dataTable.add(sheet);
        }
        return dataTable;
    }

    public static Map<String,List<List<Object>>> readDataWithSheetNameFromExcel(String filename) throws IOException, InvalidFormatException {
        String excelPath = Paths.get("src/test/resources/excelData/" + filename + ".xlsx").toAbsolutePath().toString();
        System.out.println("Reading Excel from : " + excelPath);
        XSSFWorkbook workbook = new XSSFWorkbook(new File(excelPath));
        Iterator<Sheet> sheetIterator = workbook.iterator();
        dataTableWithSheetName = new LinkedHashMap<>();
        while (sheetIterator.hasNext()) {
            Sheet sheetObject = sheetIterator.next();
            String sheetName = sheetObject.getSheetName();
            List<List<Object>> sheet = new ArrayList<>();
            Iterator<Row> rowIterator = sheetObject.rowIterator();
            while (rowIterator.hasNext()) {
                List<Object> row = new ArrayList<>();
                Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            row.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            row.add(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            row.add(cell.getBooleanCellValue());
                            break;
                        case BLANK:
                            row.add(null);
                            break;
                    }
                }
                sheet.add(row);
            }
            dataTableWithSheetName.put(sheetName,sheet);
        }
        return dataTableWithSheetName;
    }

    public static void readExcelToJsonFile(String filename) throws IOException, InvalidFormatException {
        String excelPath = Paths.get("src/test/resources/excelData/" + filename + ".xlsx").toAbsolutePath().toString();
        System.out.println("Reading Excel from : " + excelPath);
        File excelFile = new File(excelPath);
        JSONObject jsonWorkBookObject = new JSONObject();
        String jsonFileName = excelFile.getName();
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
        Iterator<Sheet> sheetIterator = workbook.iterator();
        dataTable = new ArrayList<>();
        while (sheetIterator.hasNext()) {
            JSONObject jsonSheetObject = new JSONObject();
            Sheet sheetObject = sheetIterator.next();
            Iterator<Row> rowIterator = sheetObject.rowIterator();
            Row hearderRow = rowIterator.next();
            while (rowIterator.hasNext()) {
                Row rowObject = rowIterator.next();
                Map<String,Object> row = new LinkedHashMap<>();
                Iterator<Cell> cellIterator = rowObject.cellIterator();
                Cell fisrtCell = cellIterator.next();
                int count = 1;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            row.put(hearderRow.getCell(count).getStringCellValue(),cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            row.put(hearderRow.getCell(count).getStringCellValue(),cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            row.put(hearderRow.getCell(count).getStringCellValue(),cell.getBooleanCellValue());
                            break;
                        case BLANK:
                            row.put(hearderRow.getCell(count).getStringCellValue(),cell.getStringCellValue());
                            break;
                    }
                    count++;
                }
                jsonSheetObject.put(fisrtCell.getStringCellValue(),row);
            }
            jsonWorkBookObject.put(sheetObject.getSheetName(),jsonSheetObject);
        }
        JsonObjectToFileWriter.getInstance().writeJsonToFile(jsonWorkBookObject,jsonFileName);
    }
}
