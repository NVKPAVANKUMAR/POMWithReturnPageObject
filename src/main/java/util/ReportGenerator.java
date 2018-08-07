package main.java.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportGenerator {
    public static ExtentTest logger;
    private static ExtentReports extent;
    private static ExtentHtmlReporter htmlReporter;

    public static void startReport() {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/STMExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "Qualitest");
        extent.setSystemInfo("Environment", "Automation Testing");
        extent.setSystemInfo("User Name", "PAVAN");
        htmlReporter.config().setDocumentTitle("Automation Testcase Report");
        htmlReporter.config().setReportName("Login Web_Application");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
    }

    public static void startTest(String testcaseName) {
        logger = extent.createTest(testcaseName);
    }

    public static void endReport() {
        extent.flush();
    }
}

