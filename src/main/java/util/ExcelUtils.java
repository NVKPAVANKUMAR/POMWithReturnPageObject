package main.java.util;

import main.java.config.Application_Data;
import main.java.web.ExtentBase;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.testng.Assert;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;


/**
 * @author Lohith.Shantharaju
 * Class is used to get the excel sheet values for Starss Pricing
 * project
 */
public class ExcelUtils {
    public static String currentExcelFile;
    private static Sheet ExcelWSheet;
    private static Workbook ExcelWBook;
    private static Cell Cell;
    private static Row Row;
    public TestContext context = TestContext.getContext();

    // This method is to set the File path and to open the Excel file, Pass
    // Excel Path and Sheetname as Arguments to this method

    /**
     * Opens an Excel workbook.
     *
     * @param filePath Path of the file to open.
     * @return Workbook object representing the Excel file
     * @throws IOException
     */
    public static synchronized Workbook openExcel(String filePath)
            throws Exception {
        InputStream stream = null;
        //  Workbook wb = null;
        NPOIFSFileSystem npoifs = null;
        OPCPackage pkg = null;
        try {
            if (filePath.startsWith("jar:")) {
                URL url = new URL(filePath);
                URLConnection conn = url.openConnection();
                stream = conn.getInputStream();
            } else {
                stream = new FileInputStream(filePath);
            }
        } catch (FileNotFoundException e) {
            Assert.fail("File is not present on this location: " + filePath);
        }
        try {
            if (getFileExt(filePath) == FileExt.XLS) {
                npoifs = new NPOIFSFileSystem(stream);
                ExcelWBook = WorkbookFactory.create(npoifs);
                // return new CloseableWorkbook(wb, npoifs);
            } else if (getFileExt(filePath) == FileExt.XLSX) {
                pkg = OPCPackage.open(stream);
                ExcelWBook = WorkbookFactory.create(pkg);
                // return new CloseableWorkbook(wb, pkg);
            } else
                throw new IOException(filePath + " (Invalid file extension)");
        } catch (IOException io) {
            if (npoifs != null) {
                npoifs.close();
            }
            if (pkg != null) {
                pkg.close();
            }
            throw io;
        } catch (Exception e) {
            if (npoifs != null) {
                npoifs.close();
            }
            if (pkg != null) {
                pkg.close();
            }
            throw new IOException(e.getMessage());
        }
        return ExcelWBook;
    }

    public static void setExcelFile(String SheetName)
            throws Exception {
        try {
            String excelName = null;
            String filePath = System.getProperty("user.dir") + "\\excelSheets\\";
            if (SheetName.contains("Config_Data")) {
                try {
                    excelName = "D:\\.Sirius-XM-Repo\\excel\\" + Application_Data.CONFIG_EXCEL;
                    new FileInputStream(excelName);
                    openExcel(excelName);
                } catch (FileNotFoundException e) {
                    excelName = "C:\\.Sirius-XM-Repo\\excel\\" + Application_Data.CONFIG_EXCEL;
                    openExcel(excelName);
                }
            } else {
                excelName = getExcelName();
                openExcel(filePath + excelName);
            }
            ExcelWSheet = ExcelWBook.getSheet(SheetName);
        } catch (Exception e) {
            throw (e);
        }
    }

    /**
     * This method is used to get Price from Post Tiering sheet of Excel sheet
     *
     * @param podID
     * @param pricingOpion
     * @return
     * @throws Exception
     */
    public static String getPostTieringPriceByPodIDAndGLID(String podID, String pricingOpion) throws Exception {
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        int resultRow = 0;
        int currentPriceColNo = 10, podIdColno = 0, new_Price_Nov = 26;
        int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        while (ExcelWSheet.iterator().hasNext()) {
            rowCount--;
            if (rowCount == 0) {
                break;
            }
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains("IMPACT_CATEGORY")) {
                    int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
                    for (int i = 1; i < totalRowCount; i++) {
                        org.apache.poi.ss.usermodel.Row r = ExcelWSheet.getRow(i);
                        r.getCell(podIdColno).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                        String val = r.getCell(podIdColno).getStringCellValue();
                        if (val.contains(podID)) {
                            resultRow = i;
                            String perticularValue = getCellData(resultRow, colNumber);
                            if (perticularValue.equals("Default")) {
                                if (pricingOpion.contains("CURRENT_PRICE")) {
                                    return getCellData(resultRow, currentPriceColNo);
                                } else if (pricingOpion.contains("New_Price_Nov")) {
                                    return getCellData(resultRow, new_Price_Nov);
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    /**
     * This method is used to get Price from Post Tiering sheet of Excel sheet
     *
     * @param podID
     * @param pricingOpion
     * @return
     * @throws Exception
     */
    public static String getPostTieringPriceByPodIDAndImpactCategory(String podID,
                                                                     String pricingOpion, String impactCategory) throws Exception {
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        int resultRow = 0;
        int currentPriceColNo = 10, podIdColno = 0, new_Price_Nov = 26;
        int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        while (ExcelWSheet.iterator().hasNext()) {
            rowCount--;
            if (rowCount == 0) {
                break;
            }
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains("IMPACT_CATEGORY")) {
                    int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
                    for (int i = 1; i < totalRowCount; i++) {
                        org.apache.poi.ss.usermodel.Row r = ExcelWSheet.getRow(i);
                        r.getCell(podIdColno).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                        String val = r.getCell(podIdColno).getStringCellValue();
                        if (val.contains(podID)) {
                            resultRow = i;
                            String perticularValue = getCellData(resultRow, colNumber);
                            if (perticularValue.equals(impactCategory)) {
                                if (pricingOpion.contains("CURRENT_PRICE")) {
                                    return getCellData(resultRow, currentPriceColNo);
                                } else if (pricingOpion.contains("New_Price_Nov")) {
                                    return getCellData(resultRow, new_Price_Nov);
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    /**
     * This method is used to get current pricing with respect the row number
     * provided
     *
     * @param RowNum
     * @return
     * @throws Exception
     */
    public static String getCurrentPriceByRowNumber(int RowNum, int currentPriceColno)
            throws Exception {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(currentPriceColno);
            String CellData = Cell.toString();
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get the row number by row name
     *
     * @param rowName
     * @return
     * @throws Exception
     */
    static int getRowNumber(String rowName) throws Exception {
        int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
        for (int i = 1; i < totalRowCount; i++) {
            org.apache.poi.ss.usermodel.Row r = ExcelWSheet.getRow(i);
            r.getCell(0).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            String val = r.getCell(0).getStringCellValue();
            if (val.contains(rowName)) {
                return i;
            }
        }
        throw new Exception("Reuqester row value is not present");
    }

    /**
     * Get the cell data by row number and column number
     *
     * @param RowNum
     * @param ColNum
     * @return
     * @throws Exception
     */
    public static String getCellDataUpdated(int RowNum, int ColNum) throws Exception {
        try {
            RowNum--;
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.toString();
            if (CellData.contains("E")) {
                int lastIndex = CellData.indexOf("E");
                CellData = CellData.substring(0, lastIndex);
                CellData = CellData.replace(".", "");
            }
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    // This method is to read the test data from the Excel cell, in this we are
    // passing parameters as Row num and Col num

    /**
     * Get the cell data by row number and column number
     *
     * @param RowNum
     * @param ColNum
     * @return
     * @throws Exception
     */
    public static String getCellData(int RowNum, int ColNum) throws Exception {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.toString();
            if (CellData.contains("E")) {
                int lastIndex = CellData.indexOf("E");
                CellData = CellData.substring(0, lastIndex);
                CellData = CellData.replace(".", "");
            }
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get the cell data by row number and column number
     *
     * @param RowNum
     * @param ColNum
     * @return
     * @throws Exception
     */
    public static String getCellDataConfig(int RowNum, int ColNum) throws Exception {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.toString();
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get the cell data by row number and column number
     *
     * @param RowNum
     * @param ColNum
     * @return
     * @throws Exception
     */
    public static String getCellData2(int RowNum, int ColNum) throws Exception {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.toString();
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * This method is to write in the Excel cell, Row num and Col num are the
     * parameters
     *
     * @param Result
     * @param RowNum
     * @param ColNum
     * @param newFilePath
     * @throws Exception
     */
    @SuppressWarnings({"static-access"})
    public static void setCellData(String Result, int RowNum, int ColNum,
                                   String newFilePath) throws Exception {
        try {
            Row = ExcelWSheet.getRow(RowNum);
            Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
            if (Cell == null) {
                Cell = Row.createCell(ColNum);
                Cell.setCellValue(Result);
            } else {
                Cell.setCellValue(Result);
            }
            // Constant variables Test Data path and Test Data file name
            FileOutputStream fileOut = new FileOutputStream(newFilePath);
            ExcelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            throw (e);
        }
    }

    /**
     * Gets the file extension from a string representing a file path.
     *
     * @param filePath
     * @return The file extension.
     */
    public static FileExt getFileExt(String filePath) {
        // Split path based on either '/' (*nix) or '\' (Windows)
        String[] dirSplit = filePath.split("/");
        if (dirSplit.length <= 1) {
            dirSplit = filePath.split("\\\\"); // One doubly-escaped (string +
            // regex) backslash
        }
        String fileName = dirSplit[dirSplit.length - 1];
        // Split by '.' to get file extension
        String[] fileSplit = fileName.split("\\.");
        // Disregard files without extensions
        if (fileSplit.length <= 1) {
            return FileExt.ERR;
        }
        // Determine and return proper extension
        String ext = fileSplit[fileSplit.length - 1];
        for (FileExt x : FileExt.values()) {
            if (x.toString().equals(ext.toUpperCase()))
                return x;
        }
        return FileExt.ERR;
    }

    /**
     * get Default Post Tiering Price By PodID And GLID
     *
     * @param podID
     * @param GLID
     * @param pricingOpion
     * @return
     * @throws Exception
     */
    public static String getDefaultPostTieringPriceByPodIDAndGLID(String podID, String GLID, String pricingOpion) throws Exception {
        ExcelUtils.setExcelFile(Application_Data.EXCEL_SHEET_TAB_NAME_POST_TIERING);
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        int resultRow = 0;
        int currentPriceColNo = 10, podIdColno = 0, glIDColno = 9, new_Price_Nov = 26;
        int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        while (ExcelWSheet.iterator().hasNext()) {
            rowCount--;
            if (rowCount == 0) {
                break;
            }
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains("IMPACT_CATEGORY")) {
                    int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
                    for (int i = 1; i < totalRowCount; i++) {
                        org.apache.poi.ss.usermodel.Row r = ExcelWSheet.getRow(i);
                        r.getCell(podIdColno).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                        String val = r.getCell(podIdColno).getStringCellValue();
                        if (val.contains(podID)) {
                            resultRow = i;
                            String perticularValue = getCellData(resultRow, colNumber);
                            String glID = getCellData(resultRow,
                                    glIDColno);
                            if (perticularValue.equals("Default") && glID.contains(GLID)) {
                                if (pricingOpion.contains("CURRENT_PRICE")) {
                                    return getCellData(resultRow, currentPriceColNo);
                                } else if (pricingOpion.contains("New_Price_Nov")) {
                                    return getCellData(resultRow, new_Price_Nov);
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    /**
     * get Default Post Tiering Price By PodID And GLID With Impact Category
     *
     * @param podID
     * @param GLID
     * @param pricingOpion
     * @param impactCategory
     * @return
     * @throws Exception
     */
    public static String getDefaultPostTieringPriceByPodIDAndGLIDWithImpactCategory(String podID, String GLID, String pricingOpion, String impactCategory) throws Exception {
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        int resultRow = 0;
        int currentPriceColNo = 10, podIdColno = 0, new_Price_Nov = 26, glIDColno = 9;
        int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        while (ExcelWSheet.iterator().hasNext()) {
            rowCount--;
            if (rowCount == 0) {
                break;
            }
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains("IMPACT_CATEGORY")) {
                    int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
                    for (int i = 1; i < totalRowCount; i++) {
                        org.apache.poi.ss.usermodel.Row r = ExcelWSheet.getRow(i);
                        r.getCell(podIdColno).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                        String val = r.getCell(podIdColno).getStringCellValue();
                        if (val.contains(podID)) {
                            resultRow = i;
                            String perticularValue = getCellData(resultRow, colNumber);
                            String glID = getCellData(resultRow,
                                    glIDColno);
                            if (perticularValue.equals(impactCategory) && glID.contains(GLID)) {
                                if (pricingOpion.contains("CURRENT_PRICE")) {
                                    return getCellData(resultRow, currentPriceColNo);
                                } else if (pricingOpion.contains("New_Price_Nov")) {
                                    return getCellData(resultRow, new_Price_Nov);
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    /**
     * This method is used to get Price from Post Tiering sheet of Excel sheet
     *
     * @param podID
     * @param pricingOpion
     * @return
     * @throws Exception
     */
    public static String getPostTieringPriceByPodIDAndGLID_JUNE2017(String podID,
                                                                    String pricingOpion) throws Exception {
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        int resultRow = 0;
        int newBaseRateColNo = 25, newRatePerMonthColNo = 24, currentPriceColNo = 10, price2017Colno = 17, podIdColno = 0;
        int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        while (ExcelWSheet.iterator().hasNext()) {
            rowCount--;
            if (rowCount == 0) {
                break;
            }
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains("IMPACT_CATEGORY")) {
                    int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
                    for (int i = 1; i < totalRowCount; i++) {
                        org.apache.poi.ss.usermodel.Row r = ExcelWSheet
                                .getRow(i);
                        r.getCell(podIdColno).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                        String val = r.getCell(podIdColno).getStringCellValue();
                        if (val.contains(podID)) {
                            resultRow = i;
                            String perticularValue = getCellData(resultRow,
                                    colNumber);
                            if (perticularValue.equals(Application_Data.EXCEL_SHEET_IMPACT_CATEGORY_LINKED_DEFAULT)
                                    || perticularValue.equals("Persistent")
                                    || perticularValue.equals(Application_Data.EXCEL_SHEET_IMPACT_CATEGORY_DISCOUNT)
                                    || perticularValue
                                    .equals(Application_Data.EXCEL_SHEET_IMPACT_CATEGORY_LINKED_DISCOUNT)) {
                                continue;
                            } else {
                                if (pricingOpion.contains("CURRENT_PRICE")) {
                                    return getCellData(resultRow,
                                            currentPriceColNo);
                                } else if (pricingOpion.contains(Application_Data.PRICING_OPTION_2017_PRICE_JUNE)) {
                                    if (getCellData(resultRow, price2017Colno)
                                            .contains("Y")
                                            && getCellData(resultRow,
                                            price2017Colno).contains(
                                            "Z")) {
                                        Float newRate = Float
                                                .parseFloat(getCellData(
                                                        resultRow,
                                                        newRatePerMonthColNo));
                                        Float newBase = Float
                                                .parseFloat(getCellData(
                                                        resultRow,
                                                        newBaseRateColNo));
                                        return String.format("%.2f",
                                                (newRate * newBase));
                                    } else if (getCellData(resultRow,
                                            price2017Colno).contains("K")) {
                                        return getCellData(resultRow,
                                                currentPriceColNo);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    //Price Change June 2017

    /**
     * This method is used to get Price from Post Tiering sheet of Excel sheet
     *
     * @param podID
     * @param pricingOpion
     * @return
     * @throws Exception
     */
    public static String getDefaultPostTieringPriceByPodIDAndGLIDWithImpactCategory_JUNE2017(String podID, String productName,
                                                                                             String pricingOpion, String impactCategory) throws Exception {
        //ExcelUtils.setExcelFile(Application_Data.EXCEL_SHEET_TAB_NAME_POST_TIERING);
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        int resultRow = 0;
        int newBaseRateColNo = 25, newRatePerMonthColNo = 24, currentPriceColNo = 10, price2017Colno = 17, podIdColno = 0, productNameColno = 1;
        int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        while (ExcelWSheet.iterator().hasNext()) {
            rowCount--;
            if (rowCount == 0) {
                break;
            }
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains("IMPACT_CATEGORY")) {
                    int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
                    for (int i = 1; i < totalRowCount; i++) {
                        org.apache.poi.ss.usermodel.Row r = ExcelWSheet
                                .getRow(i);
                        r.getCell(podIdColno).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                        String val = r.getCell(podIdColno).getStringCellValue();
                        if (val.contains(podID)) {
                            resultRow = i;
                            String perticularValue = getCellData(resultRow,
                                    colNumber);
                            String PRODUCT_NAME = getCellData(resultRow,
                                    productNameColno);
                            if (perticularValue.equals(impactCategory) && productName.contains(PRODUCT_NAME)) {
                                if (pricingOpion.contains("Current Price")) {
                                    return getCellData(resultRow,
                                            currentPriceColNo);
                                } else if (pricingOpion.contains(Application_Data.PRICING_OPTION_2017_PRICE_JUNE)) {
                                    if (getCellData(resultRow, price2017Colno)
                                            .contains("Y")
                                            && getCellData(resultRow,
                                            price2017Colno).contains(
                                            "Z")) {
                                        Float newRate = Float
                                                .parseFloat(getCellData(
                                                        resultRow,
                                                        newRatePerMonthColNo));
                                        Float newBase = Float
                                                .parseFloat(getCellData(
                                                        resultRow,
                                                        newBaseRateColNo));
                                        return String.format("%.2f",
                                                (newRate * newBase));
                                    } else if (getCellData(resultRow,
                                            price2017Colno).contains("K")) {
                                        return getCellData(resultRow,
                                                currentPriceColNo);
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    /**
     * get Default Post Tiering Price By PodID And ProductName And GLID With Impact Category
     *
     * @param podID
     * @param productName
     * @param glid
     * @param pricingOpion
     * @param impactCategory
     * @return
     * @throws Exception
     */
    public static String getDefaultPostTieringPriceByPodIDAndProductNameAndGLIDWithImpactCategory(String podID, String productName, String glid,
                                                                                                  String pricingOpion, String impactCategory) throws Exception {
        //ExcelUtils.setExcelFile(Application_Data.EXCEL_SHEET_TAB_NAME_POST_TIERING);
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        int resultRow = 0;
        int newBaseRateColNo = 25, newRatePerMonthColNo = 24, currentPriceColNo = 10, glidColNo = 9, price2017Colno = 17, podIdColno = 0, productNameColno = 1;
        int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        while (ExcelWSheet.iterator().hasNext()) {
            rowCount--;
            if (rowCount == 0) {
                break;
            }
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains("IMPACT_CATEGORY")) {
                    int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
                    for (int i = 1; i < totalRowCount; i++) {
                        org.apache.poi.ss.usermodel.Row r = ExcelWSheet
                                .getRow(i);
                        r.getCell(podIdColno).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                        String val = r.getCell(podIdColno).getStringCellValue();
                        if (val.contains(podID)) {
                            resultRow = i;
                            String perticularValue = getCellData(resultRow,
                                    colNumber);
                            String PRODUCT_NAME = getCellData(resultRow,
                                    productNameColno);
                            String GL_ID = getCellData(resultRow, glidColNo);
                            if (perticularValue.equals(impactCategory) && productName.contains(PRODUCT_NAME))
                                while (GL_ID.contains(glid)) {
                                    if (pricingOpion.contains("Current Price")) {
                                        //									if (perticularValue.equals(glid) && GL_ID.contains(glid)){
                                        return getCellData(resultRow,
                                                currentPriceColNo);
                                    } else if (pricingOpion.contains(Application_Data.PRICING_OPTION_2017_PRICE_JUNE)) {
                                        if (getCellData(resultRow, price2017Colno)
                                                .contains("Y")
                                                && getCellData(resultRow,
                                                price2017Colno).contains(
                                                "Z")) {
                                            Float newRate = Float
                                                    .parseFloat(getCellData(
                                                            resultRow,
                                                            newRatePerMonthColNo));
                                            Float newBase = Float
                                                    .parseFloat(getCellData(
                                                            resultRow,
                                                            newBaseRateColNo));
                                            return String.format("%.2f",
                                                    (newRate * newBase));
                                        } else if (getCellData(resultRow,
                                                price2017Colno).contains("K")) {
                                            return getCellData(resultRow,
                                                    currentPriceColNo);
                                        }
                                    }
                                }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        //		}
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    /**
     * Select Excel version based on package with fully qualified path of excel
     *
     * @return excel Version
     */
    public static String getExcelName() {
        Package packageName = ExtentBase.packageName;
        String excelVersion = "";
        String pack = packageName.toString();
        pack = pack.replace("package ", "");
        switch (pack) {
            case "com.siriusxm.auto.framework.project_1002715_Nov_2017_Price_Increase":
                excelVersion = Application_Data.EXCEL_DATA_FILE_VERSION_4;
                break;
            default:
                excelVersion = Application_Data.EXCEL_DATA_FILE_VERSION_2;
                break;
        }
        return excelVersion;
    }

    /**
     * get Default Post Tiering Price By PodID And GLID With Impact Category
     *
     * @param podID
     * @param GLID
     * @param pricingOpion
     * @param impactCategory
     * @return
     * @throws Exception
     */
    public static String getAmountByPodID_GLIDAndImpactCategory(String podID, String GLID,
                                                                String pricingOpion, String impactCategory) throws Exception {
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        int resultRow = 0;
        int currentPriceColNo = 10, podIdColno = 0, glIDColno = 9, newBaseRateColNo = 25, newRatePerMonthColNo = 24, price2017Colno = 17, newRatePerMonthOldColNo = 22, termLength = 2, New_Price_Nov = 30;
        int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        while (ExcelWSheet.iterator().hasNext()) {
            rowCount--;
            if (rowCount == 0) {
                break;
            }
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains("IMPACT_CATEGORY")) {
                    int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
                    for (int i = 1; i < totalRowCount; i++) {
                        org.apache.poi.ss.usermodel.Row r = ExcelWSheet
                                .getRow(i);
                        r.getCell(podIdColno).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                        String val = r.getCell(podIdColno).getStringCellValue();
                        if (val.contains(podID)) {
                            resultRow = i;
                            String perticularValue = getCellData(resultRow,
                                    colNumber);
                            String glID = getCellData(resultRow,
                                    glIDColno);
                            if (perticularValue.equals(impactCategory) && glID.contains(GLID)) {
                                if (pricingOpion.contains("CURRENT_PRICE")) {
                                    return getCellData(resultRow, currentPriceColNo);
                                } else if (pricingOpion.contains("New_Price_Nov")) {
                                    return getCellData(resultRow,
                                            New_Price_Nov);
                                }
                            } else if (pricingOpion.contains("New Price")) {
                                if (getCellData(resultRow, price2017Colno).contains("Y") && getCellData(resultRow, price2017Colno).contains("Z")) {
                                    String new_base_rate = getCellDataUpdated(resultRow, newBaseRateColNo);
                                    String new_rate_per_month_calculation = getCellDataUpdated(resultRow, newRatePerMonthColNo);
                                    if (new_rate_per_month_calculation.contains("W")) {
                                        new_rate_per_month_calculation = getCellData(resultRow, newRatePerMonthOldColNo);
                                    }
                                    if (new_rate_per_month_calculation.contains("C")) {
                                        new_rate_per_month_calculation = getCellData(resultRow, termLength);
                                    }
                                    Float newRate = Float.parseFloat(new_base_rate);
                                    Float newBase = Float.parseFloat(new_rate_per_month_calculation);
                                    return String.format("%.2f", (newRate * newBase));
                                } else if (getCellData(resultRow, price2017Colno).contains("K")) {
                                    return getCellData(resultRow, currentPriceColNo);
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    public static String getApplicationCofigData(String rowName) {
        try {
            setExcelFile("Config_Data");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        int resultRow = 0;
        String finalValue = "";
        int argumentsColNum = 0;
        int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        while (ExcelWSheet.iterator().hasNext()) {
            rowCount--;
            if (rowCount == 0) {
                break;
            }
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains("Values")) {
                    int totalRowCount = ExcelWSheet.getPhysicalNumberOfRows();
                    for (int i = 1; i < totalRowCount; i++) {
                        org.apache.poi.ss.usermodel.Row r = ExcelWSheet.getRow(i);
                        r.getCell(argumentsColNum).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                        String val = r.getCell(argumentsColNum).getStringCellValue();
                        if (val.contains(rowName)) {
                            resultRow = i;
                            try {
                                finalValue = getCellDataConfig(resultRow, colNumber);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return finalValue;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    public static String getTestDataFromExcel(String columnName) {
        try {
            setExcelFileLocation("DemoMultipleAccounts");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        org.apache.poi.ss.usermodel.Row row;
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator;
        String finalValue = "";
        while (ExcelWSheet.iterator().hasNext()) {
            row = ExcelWSheet.iterator().next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                int colNumber = cell.getColumnIndex();
                if (value.contains(columnName)) {
                    try {
                        finalValue = getCellData2(0, colNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return finalValue.trim();
                }
                continue;
            }
        }
        Assert.fail("Test case failed because the value searched does not exist in current excel sheet or usage of wrong excel sheet");
        return null;
    }

    public static int getRowCount() {
        int rowCount, count = 0;
        String getCellValue = "";
        try {
            setExcelFileLocation("DemoMultipleAccounts");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        rowCount = ExcelWSheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rowCount; i++) {
            try {
                getCellValue = getCellData2(i, 0);
            } catch (Exception e) {
                ExtentBase.addComment("Unable to get row count");
            }
            if (!(getCellValue.equals(""))) {
                count++;
            } else if (getCellValue.equals("")) {
                break;
            }
        }
        return count;
    }

    /**
     * set Excel File Location
     *
     * @param SheetName
     * @throws Exception
     */
    public static void setExcelFileLocation(String SheetName)
            throws Exception {
        try {
            String excelName = null;
            String filePath = Application_Data.PRICING_DATA_SHEET;
            String testCaseName = ExtentBase.testCaseName;
            if (testCaseName.contains("_191__CreateAccount")) {
                excelName = Application_Data.ACCOUNT_CREATION_DATA;
            } else if (testCaseName.contains("_192__CreateAccount_AddRadio")) {
                excelName = Application_Data.ACCOUNT_CREATION_ADD_RADIO_DATA;
            } else if (testCaseName.contains("_193__CreateAccount_CloseRadio_ReactivateRadio")) {
                excelName = Application_Data.ACCOUNT_CREATION_CLOSERADIO;
            }
            openExcel(filePath + excelName);
            currentExcelFile = filePath + excelName;
            ExcelWSheet = ExcelWBook.getSheet(SheetName);
        } catch (Exception e) {
            throw (e);
        }
    }

    /**
     * File extension enumerator. Denotes valid file extensions, for use in
     * comparisons and such.
     *
     * @author Lohith
     */
    public enum FileExt {
        XLS, // Excel '97
        XLSX, // Excel '07
        CSV, // Comma-separated value
        XML, // XML
        TXT, // Text
        LOG, // Log
        // DB, // Database (curr. unsupported)
        SQL, // SQL query files
        HTML, // HTML
        JAR, // JAR files
        ERR; // Unsupported/invalid extension
    }
}