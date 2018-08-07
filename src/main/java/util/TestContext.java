package main.java.util;

import io.appium.java_client.AppiumDriver;
import main.java.pom.POM;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Singleton class for common context within a test run.
 */
public class TestContext {

    // Singleton
    // private static TestContext me = null;

    private static final ThreadLocal<TestContext> me = new ThreadLocal<TestContext>() {
        @Override
        protected TestContext initialValue() {
            return new TestContext();
        }
    };

    private static WebDriver driver;
    private static AppiumDriver appiumDriver;
    private static String testFilePath;
    public String currentTestCase;
    //	 private WindowManagerThread windowManagerThread;
    private WindowStack wStack;
    private Data data;
    private volatile HashMap<String, Object> storage;
    private volatile HashMap<String, Object> globalStorage;
    private HashMap<String, Connection> connections;
    private Connection latestConn;
    private String latestConnName;
    private Logger log;
    private int browserTimeout = 30; // default 30s

    private TestContext() {
        connections = new HashMap<String, Connection>();
        storage = new HashMap<String, Object>();
        globalStorage = new HashMap<String, Object>();
    }

    /**
     * Returns the singleton TestContext object.
     *
     * @return
     */
    public static TestContext getContext() {

        return me.get();
    }

    public int getBrowserTimeout() {
        return browserTimeout;
    }

    public void setBrowserTimeout(int browserTimeout) {
        this.browserTimeout = browserTimeout;
    }

    public void closeBrowser() {
        setLogger();
        log(Logger.LogLevel.debug, "Stopping window manager thread.");
        /*
         * if(windowManagerThread != null) { windowManagerThread.kill();
         * while(windowManagerThread.isAlive()) { // wait until thread dies try
         * { webElementHelper.suspend(1000); } catch (InterruptedException e) { // TODO
         * Auto-generated catch block e.printStackTrace(); }
         * System.out.print('.'); } addComment(); windowManagerThread =
         * null; }
         */
        // TODO clear out windowStack
        log(Logger.LogLevel.debug, "Closing driver...");
        /*
         * driver.quit(); return; /
         */

        // Preserved just in case
        // Repeatedly try to close all windows
        try {
            int latestSize = Integer.MAX_VALUE;
            int loops = 0;
            for (int size = driver.getWindowHandles().size(); size > 0; size = driver
                    .getWindowHandles().size()) {
                if (size < latestSize) {
                    latestSize = size;
                    loops = 0;
                } else {
                    loops++;
                }

                if (loops > 100) {
                    throw new RuntimeException(
                            "Unable to close browser windows");
                }

                for (String window : driver.getWindowHandles()) {

                    try {
                        driver.switchTo().window(window);
                        log(Logger.LogLevel.debug, "Closing window " + window);
                        log(Logger.LogLevel.debug, driver.getTitle());
                        String userAgent = (String) ((JavascriptExecutor) driver)
                                .executeScript("return navigator.userAgent;",
                                        new Object[0]);
                        log(Logger.LogLevel.debug, userAgent);
                        if (userAgent.toLowerCase().contains("msie 8.0")) {
                            ((JavascriptExecutor) driver).executeScript(
                                    "window.open('','_self').close();",
                                    new Object[0]);
                        } else {
                            driver.close();
                        }
                    } catch (Exception e) {
                        // System.err.println(e.getMessage());
                        // e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        log(Logger.LogLevel.debug, "Quitting driver " + driver.toString());
        try {
            driver.quit();
        } catch (Exception e) {
            // suppress it, whatever
            // e.printStackTrace();
        }
        driver = null;
        log(Logger.LogLevel.debug, "Driver closed");
        // */
    }

    /**
     * Cleans up any remaining data in the
     */
    public void cleanup() {
        if (driver != null) {
            closeBrowser();
        }
        for (Connection conn : connections.values())
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        data = null;
        storage.clear();
    }

    /**
     * Sets the WebDriver for the current test run.
     *
     * @param wd
     */
    public void setWebDriver(WebDriver wd, boolean windowStack) {
        setLogger();
        driver = wd;
        POM.setDriver(driver);
        log(Logger.LogLevel.debug, "Setting web driver " + driver.toString());

        /*
         * //TODO This needs to change when we start managing multiple drivers
         * // as it kills the thread managing old driver's windows
         * if(windowManagerThread != null) { windowManagerThread.kill(); try {
         * webElementHelper.suspend(WindowManagerThread.WAIT_INTERVAL*2000); } catch
         * (InterruptedException e) { // ignore } }
         */

        if (windowStack) {
            wStack = new WindowStack(driver);
            wStack.takeSnapshot();
            // windowManagerThread.start();
        } else {
            wStack = null;
        }
    }

    public WindowStack getWindowStack() {
        if (wStack == null) {
            throw new IllegalStateException(
                    "Window Stack is not corrently enabled. Please add a \"WindowStack\" argument to the "
                            + "Browser.Launch keyword in your test file, with value \"Y\" to enable it.");
        } else {
            return wStack;
        }
    }

    /**
     * Sets the log level for the current test run.
     *
     * @param logLevel
     */
    public void setLog(int logLevel) {
        setLogger();
        log.changeLogLevel(Logger.LogLevel.get(logLevel));
    }

    public void setConn(Connection conn, String name) {
        connections.put(name, conn);
        latestConnName = name;
        latestConn = conn;
    }

    /**
     * Returns the current WebDriver.
     *
     * @return
     */
    public WebDriver getWebDriver() {
        return driver;
    }

    /**
     * Returns the current data set.
     *
     * @return
     */
    public Data getData() {
        return data;
    }

    /**
     * Sets the data set for the current test run.
     *
     * @param data
     */
    public void setData(Data data) {
        this.data = data;
    }

    public Connection getConn() {
        return latestConn;
    }

    public void setConn(Connection conn) {
        connections.put("", conn);
        latestConnName = "";
        latestConn = conn;
    }

    public String getConnName() {
        return latestConnName;
    }

    public Connection getConn(String name) {
        return connections.get(name);
    }

    public String getTestFilePath() {
        return testFilePath;
    }

    public void setTestFilePath(String filePath) {
        testFilePath = filePath;
    }

    public synchronized void store(String key, Object value) {
        storage.put(key, value);
    }

    public synchronized Object retrieve(String key) {
        return storage.get(key);
    }

    public synchronized void storeGlobal(String key, Object value) {
        globalStorage.put(key, value);
    }

    public synchronized Object retrieveGlobal(String key) {
        return globalStorage.get(key);
    }

    /**
     * Writes a log message.
     *
     * @param level   The Log level of the message
     * @param message
     */
    public void log(Logger.LogLevel level, String message) {
        setLogger();
        message = "[T:" + Thread.currentThread().getId() + "] " + message;
        switch (level) {
            case info:
                log.debug(message);
                break;
            case error:
                log.error(message);
                break;
            case fatal:
                log.fatal(message);
                break;
            case debug:
                log.debug(message);
                break;
            case trace:
                log.trace(message);
                break;
            case warn:
                log.warning(message);
                break;
            default:
                break;
        }
    }

    public Logger.LogLevel getLogLevel() {
        setLogger();
        return log.getLogLevel();
    }

    public void setLogger() {
        if (log == null) {
            log = new Logger();
        }
    }

    /**
     * Class for storing variable-to-value mappings for the test context.
     */
    public static class Data extends HashMap<String, String> {
        /**
         *
         */
        private static final long serialVersionUID = 7287830677076141358L;

        private int rowIndex;

        public Data(int initialCapacity) {
            super(initialCapacity);
        }

        public Data() {
            super();
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }
    }

}
