package main.java.web;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;
import main.java.config.Application_Data;
import main.java.helper.Browser;
import main.java.helper.CommonHelper;
import main.java.helper.ScreenshotHelper;
import main.java.helper.WebElementHelper;
import main.java.reporting.ExtentManager;
import main.java.reporting.ExtentTestManager;
import main.java.util.ExcelUtils;
import main.java.util.TestContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This Class is used to perform all the action that needs to be done before and
 * after each test case execution
 *
 * @author Vinith.Arjun
 */
public class ExtentBase {
    static public Logger logger;
    public static boolean isCreateAccount = false;
    public static TestContext context = TestContext.getContext();
    public static int errorCount = 0;
    public static boolean executionInProgress = false;
    public static List<String> myList = new ArrayList<String>();
    public static List<String> failureList = new ArrayList<String>();
    public static List<Date> myListForTimeStamp = new ArrayList<Date>();
    public static Date currentTimeStamp = null;
    public static Package packageName = null;
    public static int reRunCount = 0;
    public static String testCaseName;
    public static String reportFolderDate;
    static ExtentTest test;
    public String logFilePath = "", logFileName = "";
    Browser appLaunch = new Browser();
    String wss = "", pin = "";
    boolean skippedTest = false;
    CommonHelper commonHelper = new CommonHelper(null);
    private Date Startdate = null;

    private static void captureScreenshot() {
        String screenshotPath = ScreenshotHelper.createScreenshot(
                context.getWebDriver(), Application_Data.IMAGE_PATH,
                ExtentManager.imageLocation);
        myList.add(screenshotPath);
        Calendar cal1 = Calendar.getInstance();
        currentTimeStamp = cal1.getTime();
        myListForTimeStamp.add(currentTimeStamp);
    }

    public static void captureWindowScreenshot() {
        String screenshotPath = ScreenshotHelper.createWindowScreenshot(
                Application_Data.IMAGE_PATH,
                ExtentManager.imageLocation);
        myList.add(screenshotPath);
        Calendar cal1 = Calendar.getInstance();
        currentTimeStamp = cal1.getTime();
        myListForTimeStamp.add(currentTimeStamp);
    }

    /**
     * taking screenshot and adding to report
     */
    public static void takeScreenshot() {
        if (context.getWebDriver().toString().toLowerCase().contains("chrome")) {
            captureScreenshot();
            new WebElementHelper(context.getWebDriver()).pageDown(context.getWebDriver());
            captureScreenshot();
        } else {
            captureScreenshot();
        }
    }

    /**
     * Add comment in extent report and log
     *
     * @param logMessage
     */
    public static void addComment(String logMessage) {
        PropertyConfigurator
                .configure("src/main/resources/log4jInfo.properties");
        logger.info(logMessage);
        myList.add(logMessage);
        Calendar cal1 = Calendar.getInstance();
        currentTimeStamp = cal1.getTime();
        myListForTimeStamp.add(currentTimeStamp);

    }

    /**
     * Add comment in extent report and log
     *
     * @param logMessage
     */
    public static void addErrorComment(String logMessage) {
        PropertyConfigurator
                .configure("src/main/resources/log4jInfo.properties");
        errorCount++;
        logger.info(logMessage);
        myList.add("@Error@" + logMessage);
        Calendar cal1 = Calendar.getInstance();
        currentTimeStamp = cal1.getTime();
        myListForTimeStamp.add(currentTimeStamp);
    }

    /**
     * Add comment in extent report and log
     *
     * @param logMessage
     */
    public static void addSelectedPackageName(String logMessage) {
        PropertyConfigurator
                .configure("src/main/resources/log4jInfo.properties");

        logger.info(logMessage);
        myList.add("@Package@" + logMessage);
        Calendar cal1 = Calendar.getInstance();
        currentTimeStamp = cal1.getTime();
        myListForTimeStamp.add(currentTimeStamp);
    }

    /**
     * Add webservice response comment in log
     *
     * @param logMessage
     */
    public static void addResponseInLog(String logMessage) {
        PropertyConfigurator
                .configure("src/main/resources/log4jInfo.properties");
        logger.info(logMessage);

    }

    /**
     * Before suite we are resetting the password for the pins
     *
     * @param context
     * @throws Exception
     */
    @BeforeSuite
    public void beforeSuite(ITestContext context) throws Exception {
        testCaseName = this.getClass().getSimpleName();
    }

    /**
     * Before Class we are setting loggers
     *
     * @throws Exception
     */

    @BeforeClass
    public void beforeClass() throws Exception {


    }

    /**
     * Before method to initialize extentReport
     *
     * @param method
     * @throws Exception
     */
    @BeforeMethod
    public void beforeMethod(Method method) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("-MM-dd-yyyy_HHmmss");
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        logFileName = "log_" + this.getClass().getSimpleName() + "_"
                + currentTime + ".txt";
        logFilePath = Application_Data.LOG_PATH + logFileName;
        System.setProperty("logfile.name", logFilePath);
        PropertyConfigurator
                .configure("src/main/resources/log4jInfo.properties");
        logger = Logger.getLogger(this.getClass());
        logger.setLevel(Level.INFO);
        Calendar cal1 = Calendar.getInstance();
        Startdate = cal1.getTime();

        Class<?> c = this.getClass();
        packageName = c.getPackage();
        String excelName = ExcelUtils.getExcelName();
        ExtentBase.addResponseInLog("Excel Version : " + excelName);
        addResponseInLog("======================================================================================================================");
    }

    /**
     * AfterMethod to check the status of test case execution and log result
     *
     * @param result
     * @throws IOException
     */
    @AfterMethod
    protected void afterMethod(ITestResult result, Method method) throws IOException {
        if (result.getStatus() == ITestResult.SKIP) {
            skippedTest = true;
            myList.clear();
            myListForTimeStamp.clear();
            errorCount = 0;
        } else {
            Class<?> c = this.getClass();
            packageName = c.getPackage();
            String suiteName = packageName.toString()
                    .substring(packageName.toString().lastIndexOf(".") + 1);
            test = ExtentTestManager.startTest(method.getName());
            ExtentTestManager.getTest().assignCategory(suiteName);

            test = ExtentTestManager.getTest();

            for (int i = 0; i <= myList.size() - 1; i++) {
                if (myList.get(i).contains("Screenshot_")) {
                    String path = test.addScreenCapture(myList.get(i));
                    test.log(LogStatus.INFO, "Refer Screenshot : ", path);

                } else if (myList.get(i).contains("@Error@")) {
                    String logMessage = myList.get(i).replace("@Error@", "");
                    test.log(LogStatus.FAIL, "<font color=\"red\"/><b>" + logMessage
                            + "</b></font>");
                } else if (myList.get(i).contains("FILES downloaded")) {
                    test.log(LogStatus.PASS,
                            "<a href='" + myList.get(i) + "'><span><font color=\"blue\"/>Click Here to View the file downloaded: Original File : Before Updation : " + myList.get(i).substring(myList.get(i).lastIndexOf("-----")) + "</font></span></a>");

                } else if (myList.get(i).contains("FILES Uploaded")) {
                    test.log(LogStatus.PASS,
                            "<a href='" + myList.get(i) + "'><span><font color=\"blue\"/>Click Here to View the file uploaded : Updated File : After Updation :" + myList.get(i).substring(myList.get(i).lastIndexOf("-----")) + "</font></span></a>");

                } else if (myList.get(i).contains("@WSDL_UPDATE@")) {
                    test.log(LogStatus.PASS, "<font color=\"brown\"/><b>" + myList.get(i).replace("@WSDL_UPDATE@", "") + "<b></font>");
                } else if (myList.get(i).contains("@Package@")) {
                    test.log(LogStatus.PASS, "<font color=\"brown\"/><b>" + myList.get(i).replace("@Package@", "") + "</b></font>");
                } else {
                    test.log(LogStatus.PASS, "<font color=\"green\"/>" + myList.get(i)
                            + "</font>");
                }
            }
            test.setStartedTime(Startdate);

            Calendar cal1 = Calendar.getInstance();
            Date endDate = cal1.getTime();
            test.setEndedTime(endDate);
            int i = 0;
            for (Log log : test.getTest().getLogList()) {
                try {
                    log.setTimestamp(myListForTimeStamp.get(i));
                    i++;
                } catch (IndexOutOfBoundsException e) {

                }
            }

            if (result.getStatus() == ITestResult.FAILURE) {
                failureList.add(packageName.toString().replace("package", "").trim() + "." + this.getClass().getSimpleName());
                Throwable exception = result.getThrowable();
                String path = "";
                String execeptionMessage = exception.getMessage();

                test.log(
                        LogStatus.FAIL,
                        " ************************************************************************************ ");
                test.log(LogStatus.FAIL, "<font color=\"red\"/><b>" + "Test case threw Exception: " + execeptionMessage
                        + "</b></font>");
                test.log(LogStatus.FAIL, " * Test case : "
                        + this.getClass().getSimpleName() + "  Failed");
                test.log(LogStatus.FAIL, " - Refer Screenshot : ", path);

                test.log(
                        LogStatus.FAIL,
                        " ************************************************************************************ ");
                test.log(LogStatus.FAIL,
                        "<a href='" + logFilePath + "'><span class='label info'><font size=\"+1\" color=\"blue\"/><b>Click Here to View the Logs Generated</b></font></span></a>");
                test.log(
                        LogStatus.FAIL,
                        " ************************************************************************************ ");
                logger.info(" ************************************************************************************ ");
                logger.info("Test case threw Exception: " + execeptionMessage);
                logger.info(" * Test case : " + this.getClass().getSimpleName()
                        + "  Failed");
                logger.info(" ************************************************************************************ ");
            } else if (errorCount > 0) {
                failureList.add(packageName.toString().replace("package", "").trim() + "." + this.getClass().getSimpleName());
                logger.info(" ************************************************************************************ ");
                logger.info(" * Test case : " + this.getClass().getSimpleName()
                        + "  Failed");
                logger.info(" ************************************************************************************ ");
                test.log(
                        LogStatus.FAIL,
                        " ************************************************************************************ ");
                test.log(LogStatus.FAIL, " * Test case : "
                        + this.getClass().getSimpleName() + "  Failed");
                test.log(
                        LogStatus.FAIL,
                        " ************************************************************************************ ");
                test.log(LogStatus.FAIL,
                        "<a href='" + logFilePath + "'><span class='label info'><font size=\"+1\" color=\"blue\"/><b>Click Here to View the Logs Generated</b></font></span></a>");
                test.log(
                        LogStatus.FAIL,
                        " ************************************************************************************ ");
            } else {
                test.log(LogStatus.PASS, "Test passed");
                test.log(
                        LogStatus.PASS,
                        " ************************************************************************************ ");
                test.log(LogStatus.PASS, " * Test case : "
                        + this.getClass().getSimpleName() + "  Passed");
                test.log(
                        LogStatus.PASS,
                        " ************************************************************************************ ");
                logger.info(" ************************************************************************************ ");
                logger.info(" * Test case : " + this.getClass().getSimpleName()
                        + "  Passed");
                test.log(
                        LogStatus.PASS,
                        " ************************************************************************************ ");
                test.log(LogStatus.PASS,
                        "<a href='" + logFilePath + "'><span class='label info'><font size=\"+1\" color=\"blue\"/><b>Click Here to View the Logs Generated</b></font></span></a>");
                logger.info(" ************************************************************************************ ");
            }
        }
        myList.clear();
        myListForTimeStamp.clear();
        System.gc();
        if (isCreateAccount) {
            errorCount = 0;

            ExtentManager.getReporter().endTest(test);
            ExtentManager.getReporter().flush();

            myList.clear();
            myListForTimeStamp.clear();
            skippedTest = false;
            List<String> cmdAndArgs = Arrays.asList("cmd", "/c",
                    "KillAll_Drivers.bat");
            File dir = new File(Application_Data.KILL_DRIVER_BAT_PATH);
            ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
            pb.directory(dir);
            pb.start();
            context.cleanup();
            new WebElementHelper(null).suspend(2000);

            ExtentManager.getReporter().flush();
        }
    }

    /**
     * After class is used to kill all drivers and close all open browsers
     *
     * @throws IOException
     */
    @AfterClass
    protected void afterClass() throws IOException {

        errorCount = 0;

        ExtentManager.getReporter().endTest(test);
        ExtentManager.getReporter().flush();

        myList.clear();
        myListForTimeStamp.clear();
        skippedTest = false;
        List<String> cmdAndArgs = Arrays.asList("cmd", "/c",
                "KillAll_Drivers.bat");
        File dir = new File(Application_Data.KILL_DRIVER_BAT_PATH);
        ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
        pb.directory(dir);
        pb.start();
        context.cleanup();
        new WebElementHelper(null).suspend(2000);
    }

    /**
     * AfterSuite to close extent Manager
     *
     * @throws IOException
     */
    @AfterSuite
    protected void afterSuite() throws IOException {
        ExtentManager.getReporter().flush();
    }
}