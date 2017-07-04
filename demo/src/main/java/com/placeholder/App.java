package com.placeholder;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Workbook wb = new HSSFWorkbook();
        Font font = wb.createFont();
        CellStyle cs = wb.createCellStyle();
        cs.setFont(font);
        cs.setWrapText(true);
        font.setBold(true);
        font.setColor((short)0xff);
        Sheet sheet = wb.createSheet("报表");
        Row row = sheet.createRow(0);
        for (int i=0; i<10; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cs);
            cell.setCellValue("jiachangcolumn: " + i);
        }


        FileOutputStream fos = new FileOutputStream("workbook.xls");
        wb.write(fos);
        fos.close();
        wb.close();
    }
}
