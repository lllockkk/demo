package com.placeholder.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class Test {
    public static void main(String[] args) throws Exception {
        try (InputStream is = new FileInputStream("/home/l/Desktop/e.xls")) {
            Workbook wb = new HSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            StringBuilder sb = new StringBuilder();
            for (Row row : sheet) {
                for (Cell cell : row) {
                    sb.append(cell.getAddress().toString()).append("=").append(getCellValue(cell)).append(" ");
                }
                sb.append("\n");
            }
            Cell cell = sheet.getRow(2).getCell(1);
            System.out.println(sb);
        }
    }

    private static String getCellValue(Cell cell) {
        CellType cellTypeEnum = cell.getCellTypeEnum();
        switch (cellTypeEnum) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BLANK:
                return "[BLANK]";
        }
        return null;
    }
}
