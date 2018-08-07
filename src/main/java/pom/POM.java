package main.java.pom;

import com.github.webdriverextensions.WebDriverExtensionsContext;
import org.openqa.selenium.WebDriver;

public final class POM {
    private POM() {
    }

    /**
     * Sets the singleton driver instance to enable page object functionality
     *
     * @param driver
     */
    public static void setDriver(WebDriver driver) {
        WebDriverExtensionsContext.setDriver(driver);
    }

    /**
     * Gets the singleton driver instance for page objects
     *
     * @return driver
     */
    public static WebDriver getWebDriver() {
        return WebDriverExtensionsContext.getDriver();
    }
}
