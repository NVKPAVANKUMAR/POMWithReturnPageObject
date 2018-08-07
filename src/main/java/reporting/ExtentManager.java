package main.java.reporting;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import main.java.config.Application_Data;
import main.java.config.Config;
import main.java.helper.CommonHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Class used for ExtentReport managing
 *
 * @author vinith.arjun
 */
public class ExtentManager {
    public static String reportLocation = Application_Data.REPORT_PATH;
    public static String imageLocation = "images\\";

    static ExtentReports extent;
    static CommonHelper commonHelper = new CommonHelper(null);
    static DateFormat dateFormat = new SimpleDateFormat("-MM-dd-yyyy_HHmmss");
    static Calendar cal = Calendar.getInstance();
    static String currentTime = dateFormat.format(cal.getTime());
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    static String fileName;
    static String filePath = reportLocation + fileName;

    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            fileName = "Report_" + "__" + CommonHelper.getReportFolderDate() + "__" + commonHelper.generateRandom() + ".html";
            filePath = Config.REPORTS_PATH + fileName;
            extent = new ExtentReports(filePath, false);
        }
        return extent;
    }
}