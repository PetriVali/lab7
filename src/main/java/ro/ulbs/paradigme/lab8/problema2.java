package ro.ulbs.paradigme.lab8;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class problema2 {
    public static void main(String[] args) {
        Map<String, Object[]> data = new TreeMap<>();
        data.put("1", new Object[]{"Name", "Surname", "Grade1", "Grade2", "Grade3", "Grade4", "Max", "Average"});
        data.put("2", new Object[]{"Amit", "Shukla", 9, 8, 7, 5});
        data.put("3", new Object[]{"Lokesh", "Gupta", 8, 9, 6, 7});
        data.put("4", new Object[]{"John", "Adwards", 8, 8, 7, 6});
        data.put("5", new Object[]{"Brian", "Schultz", 7, 6, 8, 9});

        // Create workbook & sheet
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Grades");

            // ---------- Styles ----------
            // Header style: bold + green background
            XSSFFont boldFont = workbook.createFont();
            boldFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(boldFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Yellow style for last two columns
            CellStyle yellowStyle = workbook.createCellStyle();
            yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            int rowNum = 0;
            for (Map.Entry<String, Object[]> entry : data.entrySet()) {
                Row row = sheet.createRow(rowNum);
                Object[] rowData = entry.getValue();

                int cellNum = 0;
                for (Object field : rowData) {
                    Cell cell = row.createCell(cellNum);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                    // Header formatting
                    if (rowNum == 0) {
                        cell.setCellStyle(headerStyle);
                    }
                    cellNum++;
                }

                // Skip formulas for header row
                if (rowNum > 0) {
                    int excelRow = rowNum + 1; // 1-based for Excel formulas

                    // Column G (index 6): MAX(Di:Fi)  -> MAX(D{row}:F{row})
                    Cell maxCell = row.createCell(6);
                    String maxFormula = String.format("MAX(D%d:F%d)", excelRow, excelRow);
                    maxCell.setCellFormula(maxFormula);
                    maxCell.setCellStyle(yellowStyle);

                    // Column H (index 7): AVERAGE(Di:Fi)
                    Cell avgCell = row.createCell(7);
                    String avgFormula = String.format("AVERAGE(D%d:F%d)", excelRow, excelRow);
                    avgCell.setCellFormula(avgFormula);
                    avgCell.setCellStyle(yellowStyle);
                }
                rowNum++;
            }

            // Auto-size all columns
            for (int i = 0; i < 8; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the file
            try (FileOutputStream out = new FileOutputStream("output8.xlsx")) {
                workbook.write(out);
                System.out.println("File generated: output8.xlsx");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}