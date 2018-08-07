package main.java.config;

public class Application_Data {

    //Raw Data Sheet  Excel pricing data sheet
    public static final String EXCEL_DATA_FILE_VERSION_1 = "Version_01_ 20160712_Raw_Data.xlsx";
    public static final String EXCEL_DATA_FILE_VERSION_2 = "Version_02_2017 Rate Change.xlsx";
    public static final String EXCEL_DATA_FILE_VERSION_3 = "Version_03_Raw Data 022317.xlsx";
    public static final String EXCEL_DATA_FILE_VERSION_4 = "Version_04_20170713 Raw Data.xlsx";
    public static final String PRICING_OPTION_2017_PRICE_JUNE = "New Price";
    public static final String PRICING_OPTION_2017_New_Price_Nov = "New_Price_Nov";
    public static final String EXCEL_SHEET_TAB_NAME_POST_TIERING = "Post_Tiering";
    public static final String EXCEL_SHEET_TAB_NAME_ALC = "ALC";
    public static final String EXCEL_SHEET_IMPACT_CATEGORY_LINKED_DISCOUNT = "Linked-Discount";
    public static final String EXCEL_SHEET_IMPACT_CATEGORY_LINKED_DEFAULT = "Linked-Default";
    public static final String EXCEL_SHEET_IMPACT_CATEGORY_DEFAULT = "Default";
    public static final String EXCEL_SHEET_IMPACT_CATEGORY_DISCOUNT = "Discount";
    //public static final String EXCEL_SHEET_COLUMN_NAME_CURRENT_PRICE_OR_NEW_PRICE = "CURRENT_PRICE";
    public static final String PRICING_OPTION_2017_CURRENT_PRICE_JUNE = "CURRENT_PRICE";
    public static final String CONFIG_EXCEL = "Application_Properties.xlsx";
    public static final String PRICING_DATA_SHEET = Config.BROWSER_DRIVER_PATH + "\\.Sirius-XM-Repo\\excel\\";

    //Timeout for test case execution :
    public static final int TIMEOUT = 60000 * 30;

    //Excel Pricing Data :
    public static final String ACCOUNT_CREATION_DATA = "01_CreateAccount.xlsx";
    public static final String ACCOUNT_CREATION_ADD_RADIO_DATA = "02_CreateAccount_AddRadio.xlsx";
    public static final String ACCOUNT_CREATION_CLOSERADIO = "03_CreateAccount_CloseRadio.xlsx";


    //Chrome - Browswer driver path :
    public static final String CHROME_DRIVER_PATH = Config.BROWSER_DRIVER_PATH;

    //IE - Browswer driver path :
    public static final String IE_DRIVER_PATH = Config.BROWSER_DRIVER_PATH
            + "\\.Sirius-XM-Repo\\BrowserDrivers\\IEDriverServer.exe";

    //FF - Browswer driver path :
    public static final String FIREFOX_DRIVER_PATH = Config.BROWSER_DRIVER_PATH;

    //Kill Driver - bat file path :
    public static final String KILL_DRIVER_BAT_PATH = "D:\\.QT_Repo_SXM\\BrowserDrivers\\";

    //Report Path :
    public static String REPORT_PATH = "D:\\.QT_Repo_SXM\\reports";
    //public static final String CONSOLIDATE_REPORTS_ON_THIS_PATH = "T:\\amontalto\\Selenium\\sourceCode\\FrameWork\\Sandeep\\Nov_27\\sharePoint06Dec\\";
    public static String IMAGE_PATH = "D:\\.QT_Repo_SXM\\";
    public static String LOG_PATH = "D:\\.QT_Repo_SXM\\images\\";
    //public static final String LOG_PATHtemp =Config. BROWSER_DRIVER_PATH + "\\.Sirius-XM-Repo\\Logs\\";

}
