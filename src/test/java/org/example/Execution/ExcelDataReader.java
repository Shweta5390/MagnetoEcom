package org.example.Execution;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;


public class ExcelDataReader {
    public Object[][] getDataFRomExcel(int SheetNumber) throws Exception {
        Object[][] data = null;
        String path = "C:\\Users\\HP\\IdeaProjects\\Project\\MagnetoProject\\InputFile\\Registration on magneto.xlsx";
        FileInputStream fis = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(SheetNumber);
        int lastrow = sheet.getLastRowNum();
        System.out.println(lastrow);
        XSSFRow row = sheet.getRow(0);
        int lastColumn = row.getLastCellNum();
        System.out.println(lastColumn);

        data = new Object[lastrow][lastColumn];
        for (int i = 1; i <= lastrow; i++) {
            for (int j = 0; j <= lastColumn-1; j++) {
                XSSFCell cell = sheet.getRow(i).getCell(j);
                data[i - 1][j] = getStringValue(cell);

            }

        }
        workbook.close();
        fis.close();

        return data;
    }

    public String getStringValue(XSSFCell cell) {
        String str = null;
        switch (cell.getCellType()) {
            case STRING:
                str = cell.getStringCellValue();
                break;
            case NUMERIC:
                str = String.valueOf((int) (cell.getNumericCellValue()));
                break;
            case BLANK:
                str="";
                break;
            default:
                str = "NA";
        }
        return str;

    }
}
