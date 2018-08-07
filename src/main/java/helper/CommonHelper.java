package main.java.helper;

import main.java.config.Application_Data;
import main.java.util.ExcelUtils;
import main.java.web.ExtentBase;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class CommonHelper {
    public static String webserviceName;
    public WebElementHelper webElementHelper;
    WebDriver driver;

    public CommonHelper(WebDriver driver) {
        this.driver = driver;
    }

    public static String getElementProperty(String propertyName) {
        String path = "";
        Properties p = new Properties();
        try {

            path = System.getProperty("user.dir") + "\\src\\main\\resources\\video.properties";

            FileReader reader = new FileReader(path);

            p.load(reader);
        } catch (IOException e) {

        }
        //System.out.println(p.getProperty(propertyName));
        return p.getProperty(propertyName);
    }

    public static String convertDateByFormat(String date, String currentFormat, String requiredFormat) throws ParseException {
        DateFormat temp = new SimpleDateFormat(currentFormat);
        Date dateFormat = temp.parse(date);
        DateFormat destDf = new SimpleDateFormat(requiredFormat);
        return destDf.format(dateFormat);
    }

    public static String getOmnitureDebuggerValues(WebDriver driver, String omnitureVariable) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String omnitureDebuggerValues = (String) ((JavascriptExecutor) js).executeScript("return s." + omnitureVariable);
        return omnitureDebuggerValues;
    }

    public static void verifyValuesInOmnitureDebugger(String variableName, String expectedValue, WebDriver driver) {
        String omnitureDebuggerValueActual = CommonHelper.getOmnitureDebuggerValues(driver, variableName);
        variableName = variableName.replaceAll(";", "");
        try {
            ExtentBase.addComment("Expected : CUWI Omniture Debugger Page : Verify " + variableName + ":" + expectedValue);
            Assert.assertTrue(omnitureDebuggerValueActual.contains(expectedValue));
            ExtentBase.addComment("Actual : CUWI Omniture Debugger Page : Verified " + variableName + ":" + omnitureDebuggerValueActual);
        } catch (AssertionError ex) {
            ExtentBase.addErrorComment("Actual : CUWI Omniture Debugger Page : Value displayed is " + variableName + ":" + omnitureDebuggerValueActual);
            ExtentBase.errorCount++;
        }
    }

    public static void checkSSHKeyAvailable(String pinNumber) {
        File f = new File(System.getProperty("user.dir") + "\\ppk\\" + pinNumber + ".txt");
        if (!(f.exists() && !f.isDirectory())) {
            Assert.fail("SSH key is not available for given pin " + pinNumber + " please update it");
        }
    }

    public static String getReportFolderDate() {
        DateFormat dateFormat = DateFormat
                .getDateInstance(DateFormat.MEDIUM);
        Date today = new Date();
        String strDate = dateFormat.format(today);
        String folderName = strDate.substring(0, strDate.lastIndexOf(","));
        folderName = folderName.replace(" ", "_");
        return folderName;
    }

    // Method to compare actual value and expected value
    public boolean compareString(String locator, String expectedValue) {
        boolean status = false;
        try {
            String actualValue = driver.findElement(By.xpath(locator))
                    .getText();
            if (expectedValue.equalsIgnoreCase(actualValue)) {
                status = true;
            } else
                status = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public String generateRandomNum() {
        String aToZ = "23456789";
        int n = 1;
        Random rand = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int randIndex = rand.nextInt(aToZ.length());
            res.append(aToZ.charAt(randIndex));
        }
        return res.toString();
    }

    public String generateRandom() {
        String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int n = 3;
        Random rand = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int randIndex = rand.nextInt(aToZ.length());
            res.append(aToZ.charAt(randIndex));
        }
        return res.toString();
    }

    public String getVIN() {
        String vin = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        int n = 17;
        Random rand = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int randIndex = rand.nextInt(vin.length());
            res.append(vin.charAt(randIndex));
        }
        return res.toString();
    }

    public String generatePIN() {
        return "" + ((int) (Math.random() * 9000) + 1000);
    }

    public String generatePIN1() {
        return "" + ((int) (Math.random() * 31000) + 1000);
    }

    public String trimStringToContainsLast4Digits(String textToTrim) {
        return textToTrim.substring(textToTrim.length() - 4);
    }

    public void waitclickid(String id) throws Exception {
        webElementHelper.suspend(1000);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element;

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        webElementHelper.suspend(1000);
        element.findElement(By.id(id)).click();
        webElementHelper.suspend(1000);
    }

    public String getTotalOfTwoAudioAmount(String firstAudioAmount, String secondAudioAmount) {
        float totalAmt;
        totalAmt = (float) (Float.parseFloat(firstAudioAmount) + Float.parseFloat(secondAudioAmount));
        totalAmt = BigDecimal.valueOf(totalAmt).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        //System.out.println(totalAmt);
        return String.valueOf(totalAmt);
    }

    public void assertAccountNumber(String accountNumber) {
        if (accountNumber.length() == 6 &&
                accountNumber.substring(0, 2).equals("**") == true) {

            ExtentBase.addComment("VERIFIED :Shown account number only shows last 4 digits");
        } else {
            Assert.fail("expected shown account number has more than 4 digits");
        }
    }

    public String updatedEsnWithLowerCase(String esn) {
        String updatedEsn = "";
        int count = 0;

        for (int i = 0; i < esn.length(); i++) {
            char charAt2 = esn.charAt(i);
            if (count >= 2) {
                updatedEsn = updatedEsn + charAt2;
            } else if (Character.isLetter(charAt2)) {
                charAt2 = Character.toLowerCase(charAt2);
                count++;
                updatedEsn = updatedEsn + charAt2;
            } else {
                updatedEsn = updatedEsn + charAt2;
            }

        }
        return updatedEsn;
    }

    public String getOldSubscriptionAmt(String DueAmt, String ActivationFee) {
        //String activationFeeAmt = ExcelSheet_POID_PckName.ACTIVATION_FEE_AMOUNT;
        float ChangedPlanAmt;
        ChangedPlanAmt = (float) (Float.parseFloat(DueAmt) - Float.parseFloat(ActivationFee));
        ChangedPlanAmt = BigDecimal.valueOf(ChangedPlanAmt).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        //System.out.println(ChangedPlanAmt);
        return String.valueOf(ChangedPlanAmt);
    }

    public String multiplicationOfTwoNumbers(String value1, String value2) {
        float totalAmt;
        totalAmt = (float) (Float.parseFloat(value1) * Float.parseFloat(value2));
        totalAmt = BigDecimal.valueOf(totalAmt).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        //System.out.println(totalAmt);
        String actualValue = String.valueOf(totalAmt);
        String valueDecimal = actualValue.substring(actualValue.lastIndexOf("."));
        if (valueDecimal.length() < 3) {
            actualValue = actualValue + "0";
        }
        return actualValue;
    }

    public String divisionOfTwoNumbers(String value1, String value2) {
        float totalAmt;
        totalAmt = (float) (Float.parseFloat(value1) / Float.parseFloat(value2));
        totalAmt = BigDecimal.valueOf(totalAmt).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
        //System.out.println(totalAmt);
        return String.valueOf(totalAmt);
    }

    public String addtionOfTwoNumbers(String value1, String value2) {
        float totalAmt;
        totalAmt = (float) (Float.parseFloat(value1) + Float.parseFloat(value2));
        totalAmt = BigDecimal.valueOf(totalAmt).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        //System.out.println(totalAmt);
        return String.valueOf(totalAmt);
    }

    public String subtractionOfTwoNumbers(String value1, String value2) {
        float totalAmt;
        totalAmt = (float) (Float.parseFloat(value1) - Float.parseFloat(value2));
        totalAmt = BigDecimal.valueOf(totalAmt).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        //System.out.println(totalAmt);
        String actualValue = String.valueOf(totalAmt);
        String valueDecimal = actualValue.substring(actualValue.lastIndexOf("."));
        if (valueDecimal.length() < 3) {
            actualValue = actualValue + "0";
        }
        return String.valueOf(actualValue);
    }

    public String getTotalOfThreeFee(String value1, String value2, String value3) {
        float totalAmt;
        totalAmt = (float) (Float.parseFloat(value1) + Float.parseFloat(value2) + Float.parseFloat(value3));
        totalAmt = BigDecimal.valueOf(totalAmt).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        //System.out.println(totalAmt);
        return String.valueOf(totalAmt);
    }

    public String getGiftCardPromoCode(String giftCard) {
        giftCard = giftCard.substring(0, giftCard.length() - 4);
        while (true) {
            String ramdomString = generatePIN();
            int ramdomNumber = Integer.parseInt(ramdomString.substring(0, 2));
            if (ramdomNumber < 95) {
                giftCard = giftCard + ramdomString;
                break;
            }
        }
        return giftCard;
    }

    public String generateRandomThreeInt() {
        return "" + ((int) (Math.random() * 900) + 100);
    }

    public String generateRandomTwoInt() {
        return "" + ((int) (Math.random() * 90) + 10);
    }

    public String giftCardFor20Dollar() {
        String giftCard = "101000000020" + generateRandomTwoInt();
        return giftCard;
    }

    public String giftCardFor25Dollar() {
        String giftCard = "101000000025" + generateRandomTwoInt();
        return giftCard;
    }

    public String giftCardFor150Dollar() {
        String giftCard = "301000000150" + generateRandomTwoInt();
        return giftCard;
    }

    public String getDataFromTags(String response, String tagName) {
        int begin = response.indexOf("<" + tagName + ">");
        int end = response.indexOf("</" + tagName + ">");
        String tagValue = response.substring(begin, end).replace("<" + tagName + ">", "").trim();
        return tagValue;
    }

    public String getOldSubscriptionAmountByDays(String totalAmount, String daysLeftOnPlan, String daysInPlan) {
        float OldSubscriptionAmount;
        totalAmount = totalAmount.replaceAll("[$()]", "");
        daysLeftOnPlan = daysLeftOnPlan.replaceAll("[$()]", "");
        //String daysInPlan = "153.00";
        daysInPlan = daysInPlan.replaceAll("[$()]", "");
        OldSubscriptionAmount = (float) ((Float.parseFloat(totalAmount) / Float.parseFloat(daysInPlan)) * Float.parseFloat(daysLeftOnPlan));
        OldSubscriptionAmount = BigDecimal.valueOf(OldSubscriptionAmount).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(OldSubscriptionAmount);
    }

    public String getSubTotalServiceByDays(String totalAmount, String daysLeftOnPlan, String daysInPlan) {
        float SubTotalService;
        totalAmount = totalAmount.replaceAll("[$()]", "");
        daysLeftOnPlan = daysLeftOnPlan.replaceAll("[$()]", "");
        //String daysInPlan = "181.00";
        daysInPlan = daysInPlan.replaceAll("[$()]", "");
        SubTotalService = (float) ((Float.parseFloat(totalAmount) / Float.parseFloat(daysInPlan)) * Float.parseFloat(daysLeftOnPlan));
        SubTotalService = BigDecimal.valueOf(SubTotalService).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        String actualValue = String.valueOf(SubTotalService);
        String valueDecimal = actualValue.substring(actualValue.lastIndexOf("."));
        if (valueDecimal.length() < 3) {
            actualValue = actualValue + "0";
        }
        return actualValue;

    }

    public String dateInPvtFormat(String date1) {
        String dateValue = "";
        String[] renewsDate = date1.split(" ");
        for (int i = 0; i <= renewsDate.length - 1; i++) {
            if (i == renewsDate.length - 1) {
                dateValue = dateValue + "1111";
            }
            dateValue = dateValue + renewsDate[i];
        }
        return dateValue;
    }

    public void updateTokenizationXMLUrl(String xmlPath) throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        String serviceXmlPath = currentDirectory + "\\Xmls\\webServicesXmls\\" + xmlPath;
        File xmlFile = new File(serviceXmlPath);
        BufferedReader br = new BufferedReader(new FileReader(xmlFile));
        String existingTextForPin380 = "?override=https://esbtst.siriusxm.com/";
        String existingTextForPin3801 = "TokenizationServiceEndPoint_v2";
        String replaceURL = "";
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            if (line.indexOf(existingTextForPin380) != -1) {
                String getResentVersion = line.substring(line.lastIndexOf(existingTextForPin380), line.lastIndexOf(existingTextForPin3801));
                getResentVersion = getResentVersion.replace(existingTextForPin380, "");
                replaceURL = existingTextForPin380 + getResentVersion + existingTextForPin3801;
                line = line.replace(replaceURL, "");
            }
            sb.append(line + "\n");
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(xmlFile));
        bw.write(sb.toString());
        bw.close();
    }

    public String getTotalOfAllTheValues(String... values) throws ParseException {
        float totalAmt = 0;
        for (String ele : values) {
            ele = ele.replaceAll("[$()]", "");
            totalAmt = (float) (Float.parseFloat(ele) + totalAmt);
        }
        totalAmt = BigDecimal.valueOf(totalAmt).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        String actualValue = String.valueOf(totalAmt);
        String valueDecimal = actualValue.substring(actualValue.lastIndexOf("."));
        if (valueDecimal.length() < 3) {
            actualValue = actualValue + "0";
        }
        return actualValue;
    }

    public String getUSFormat(String value) {
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        float formatedAmount = (float) (Float.parseFloat(value));
        return fmt.format(formatedAmount).replace("$", "").trim();
    }

    public String getSubOfAllTheValues(String... values) throws ParseException {
        float totalAmt = 0;
        for (String ele : values) {
            ele = ele.replaceAll("[$()]", "");
            totalAmt = (float) (Float.parseFloat(ele) - totalAmt);
        }
        totalAmt = BigDecimal.valueOf(totalAmt).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        String actualValue = String.valueOf(totalAmt);
        if (actualValue.contains("-")) {
            actualValue = actualValue.replace("-", "");
        }
        String valueDecimal = actualValue.substring(actualValue.lastIndexOf("."));
        System.out.println(valueDecimal);
        if (valueDecimal.length() < 3) {
            actualValue = actualValue + "0";
        }
        return actualValue;
    }

    public void requestInFile(String response) {
        DateFormat dateFormat = new SimpleDateFormat("-MM-dd-yyyy_HHmmss");
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        String nameFile = "RequestFile" + generateRandom() + ".txt";
        String FILENAME = "Report_FILES downloaded" + currentTime + "-----" + nameFile;
        String filePath = Application_Data.LOG_PATH + "/" + FILENAME;
        File outfile = new File(filePath);
        ExtentBase.myList.add(filePath);
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            String content = response;
            fw = new FileWriter(outfile);
            bw = new BufferedWriter(fw);
            bw.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String giftCardCode(String giftCardAmount) {
        String giftCard = "";
        if (giftCardAmount.length() == 2) {
            giftCard = "1010000000" + giftCardAmount + generateRandomTwoInt();
        }
        if (giftCardAmount.length() == 3) {
            giftCard = "101000000" + giftCardAmount + generateRandomTwoInt();
        }
        return giftCard;
    }

    public void responseInFile(String response) {
        DateFormat dateFormat = new SimpleDateFormat("-MM-dd-yyyy_HHmmss");
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        String nameFile = "responseFile" + generateRandom() + ".txt";
        String FILENAME = "Report_FILES downloaded" + currentTime + "-----" + nameFile;
        String filePath = Application_Data.LOG_PATH + "/" + FILENAME;
        File outfile = new File(filePath);
        ExtentBase.myList.add(filePath);
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            String content = response;
            fw = new FileWriter(outfile);
            bw = new BufferedWriter(fw);
            bw.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getPackageTermCode(String PackageTerm) throws Exception {

        switch (PackageTerm) {

            case "1":
                return "PreferredPlans-1_";
            case "3":
                return "PreferredPlans-3_1";
            case "6":
                return "StandardPlans-6";
            case "12":
                return "PreferredPlans-12";
            case "24":
                return "StandardPlans-24";
            case "36":
                return "StandardPlans-36";
            case "LT":
                return "OtherPlans-0_0";

            default:
                return "";

        }
    }

    public void failedTC(List<String> names) {

        Path pathOfTxt = Paths.get(Application_Data.REPORT_PATH + "\\FailedTCTxt");

        try {
            Files.createDirectories(pathOfTxt);
        } catch (IOException e) {
            System.err.println("Cannot create directories - " + e);
        }

        Path pathOfXml = Paths.get(Application_Data.REPORT_PATH + "\\FailedTCXml");

        try {
            Files.createDirectories(pathOfXml);
        } catch (IOException e) {
            System.err.println("Cannot create directories - " + e);
        }

        String xmlFirstHalf = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\r\n" +
                "<suite name=\"Suite\">\r\n" +
                "  <test name=\"Test\">\r\n" +
                "    <classes>\r\n";
        String testCase = "";
        String xmlSecondHalf = "    </classes>\r\n" +
                "  </test> <!-- Test -->\r\n" +
                "</suite> <!-- Suite -->";

        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String hostname = addr.getHostName();
        String suiteName = ExtentBase.packageName.toString().substring(ExtentBase.packageName.toString().lastIndexOf(".") + 1);
        String filePath = pathOfXml + "\\" + hostname + "_" + suiteName + "_" + "Failed_TCs_" + "_" + generateRandom() + ".xml";
        String fileText = pathOfTxt + "\\" + hostname + "_" + suiteName + "_" + "Failed_TCs_" + "_" + generateRandom() + ".txt";
        File outfile = new File(filePath);
        File outfile1 = new File(fileText);
        BufferedWriter bw = null;
        FileWriter fw = null;

        BufferedWriter bw1 = null;
        FileWriter fw1 = null;

        for (String testcaseName : names) {
            testCase = testCase + testcaseName + "\r\n";
            testCase = testCase.replaceAll("com.siriusxm.auto.framework.", "");
            //testCase = testCase.substring(testCase.indexOf(".")+1);
            testcaseName = "<class name=" + "\"" + testcaseName + "\"" + "/>\r\n";
            xmlFirstHalf = xmlFirstHalf + testcaseName;
        }
        String output = xmlFirstHalf + xmlSecondHalf;
        try {

            String content = output;

            fw = new FileWriter(outfile);
            bw = new BufferedWriter(fw);
            bw.write(content);

            fw1 = new FileWriter(outfile1);
            bw1 = new BufferedWriter(fw1);
            bw1.write(testCase);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

                if (bw1 != null)
                    bw1.close();

                if (fw1 != null)
                    fw1.close();

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }

    public void createXMLForReRun(List<String> names) {

        String xmlFirstHalf = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\r\n" +
                "<suite name=\"Suite\">\r\n" +
                "  <test name=\"Test\">\r\n" +
                "    <classes>\r\n";
        String testCase = "";
        String xmlSecondHalf = "    </classes>\r\n" +
                "  </test> <!-- Test -->\r\n" +
                "</suite> <!-- Suite -->";

        String suiteName = ExtentBase.packageName.toString().substring(ExtentBase.packageName.toString().lastIndexOf(".") + 1);
        String pathOfXml = getFilePathForXML();
        String filePath = pathOfXml + "\\" + suiteName.replace("Regression_", "").trim() + ".xml";
        File outfile = new File(filePath);
        BufferedWriter bw = null;
        FileWriter fw = null;


        for (String testcaseName : names) {
            testCase = testCase + testcaseName + "\r\n";
            testCase = testCase.replaceAll("com.siriusxm.auto.framework.", "");
            //testCase = testCase.substring(testCase.indexOf(".")+1);
            testcaseName = "<class name=" + "\"" + testcaseName + "\"" + "/>\r\n";
            xmlFirstHalf = xmlFirstHalf + testcaseName;
        }
        String output = xmlFirstHalf + xmlSecondHalf;
        try {

            String content = output;

            fw = new FileWriter(outfile);
            bw = new BufferedWriter(fw);
            bw.write(content);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }

    }

    public String getMachineName() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String hostname = addr.getHostName();
        return hostname;
    }

    public String getFilePathForXML() {
        String filePath = "";
        File folder = new File("T:\\amontalto\\Selenium\\Jenkins\\");
        File[] files = folder.listFiles();
        for (final File fileEntry : files) {
            if (fileEntry.isDirectory()) {
                if (fileEntry.getName().toLowerCase().contains(getMachineName().toLowerCase().replace("-uft", "").trim())) {
                    filePath = fileEntry.getAbsolutePath();
                }
            }
        }
        return filePath;
    }

    public void checkFornumeroAlphaNumeroFormat(String value) {
        try {
            ExtentBase.addComment("Expected : Verify " + value + " in required format");
            String[] split = value.split("-");
            Assert.assertTrue(split[0].matches("[0-9]+"), "");
            Assert.assertTrue(split[1].matches(".*[A-Z]+"), "");
            Assert.assertTrue(split[2].matches("[0-9]+"), "");
            ExtentBase.addComment("Actual : Verified " + value + "is in required format");
        } catch (AssertionError ex) {
            ExtentBase.addErrorComment("Actual : Unable to Verify " + value + " in required format");
            ExtentBase.errorCount++;
        }
    }

    public void addAccountNumberToExcelSheet(int rowCount, String accountNumber) {
        try {
            File file = new File(ExcelUtils.currentExcelFile);
            FileInputStream fis = new FileInputStream(file);
            @SuppressWarnings("resource")
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet("DemoMultipleAccounts");
            XSSFRow headerRow = sheet.getRow(0);
            String result = "";
            int resultCol = -1;
            for (Cell cell : headerRow) {
                result = cell.getStringCellValue();
                if (result.equals("Account Number")) {
                    resultCol = cell.getColumnIndex();
                    break;
                }
            }
            if (resultCol == -1) {
                System.out.println("Result column not found in sheet");
                return;
            }
            XSSFRow row = sheet.getRow(rowCount);
            XSSFCell hSSFCell = row.getCell(resultCol, Row.CREATE_NULL_AS_BLANK);
            String value = accountNumber;
            hSSFCell.setCellValue(value);
            fis.close();
            FileOutputStream outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            System.out.println();
            ;
            ExtentBase.addErrorComment("Unable to read/write the file " + ExcelUtils.currentExcelFile);
        }
    }
}

