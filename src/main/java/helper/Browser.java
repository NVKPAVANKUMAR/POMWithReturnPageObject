package main.java.helper;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import main.java.config.Application_Data;
import main.java.config.Config;
import main.java.factory.DefaultPageFactory;
import main.java.pages.HomePage;
import main.java.util.TestContext;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;


public class Browser {
    TestContext context = TestContext.getContext();
    WebDriver driver;
    AppiumDriver<WebElement> appdriver;
    AndroidDriver<WebElement> androidDriver = null;

    DefaultPageFactory factory = new DefaultPageFactory();
    WebElementHelper webElementHelper = new WebElementHelper(null);


    public WebDriver getIEDriver() {
        System.setProperty("webdriver.ie.driver", Application_Data.IE_DRIVER_PATH);
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capability.setCapability("EnableNativeEvents", false);
        capability.setCapability("ignoreZoomSetting", true);
        webElementHelper.suspend(2000);
        WebDriver driver = new InternetExplorerDriver(capability);
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.titleContains("Certificate"));
            driver.navigate().to("javascript:document.getElementById('overridelink').click()");
        } catch (Exception e) {
        }
        driver.manage().window().maximize();
        return driver;
    }


    private WebDriver getChromeDriver() {
        //System.setProperty("webdriver.chrome.driver", Application_Data.CHROME_DRIVER_PATH);
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capability.setCapability("ignoreZoomSetting", true);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(capability);
        //chromeDriver.manage().window().maximize();
        return driver;
    }

    public RemoteWebDriver getFireFoxRemoteWebDriver(String browserName, String platform, String platformName, String deviceName, String version) throws MalformedURLException {
        String marionetteDriverLocation = Application_Data.FIREFOX_DRIVER_PATH;
        System.setProperty("webdriver.gecko.driver", marionetteDriverLocation);
        // Create object of �DesiredCapabilities class�and specify android platform
        DesiredCapabilities capabilities = DesiredCapabilities.android();

        // set the capability to execute test in chrome browser
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);

        // set the capability to execute our test in Android Platform
        if (platform.equalsIgnoreCase("ANDROID")) {
            capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
        }

        // we need to define platform name
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");

        // Set the device name as well (you can give any name)
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);

        //�set the android version as well�
        capabilities.setCapability(MobileCapabilityType.VERSION, version);

        // Create object of �AndroidDriver class and pass the url and capability that we created and URL class and specify the appium server address
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        //WebDriver firefoxDriver = new FirefoxDriver();
        return driver;
    }

    public RemoteWebDriver getRemoteWebDriver(String browserName, String platform, String platformName, String deviceName, String version) throws MalformedURLException {
        // Create object of �DesiredCapabilities class�and specify android platform
        DesiredCapabilities capabilities = DesiredCapabilities.android();

        // set the capability to execute test in chrome browser
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);

        // set the capability to execute our test in Android Platform
        if (platform.equalsIgnoreCase("ANDROID")) {
            capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
        }

        // we need to define platform name
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");

        // Set the device name as well (you can give any name)
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);

        //�set the android version as well�
        capabilities.setCapability(MobileCapabilityType.VERSION, version);

        // Create object of �AndroidDriver class and pass the url and capability that we created and URL class and specify the appium server address
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        return driver;
    }

    public RemoteWebDriver getRemoteWebDriverAPP(String platform, String version, String deviceName, String browserName) throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.android();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
        capabilities.setCapability(MobileCapabilityType.VERSION, version);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.APP, browserName);
        capabilities.setCapability("appPackage", "com.axis.mobile");
        capabilities.setCapability("appActivity", "com.axis.mobile.MainActivity");
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        return driver;
    }


    public WebDriver getFireFoxDriver() {
        String marionetteDriverLocation = Application_Data.FIREFOX_DRIVER_PATH;
        System.setProperty("webdriver.gecko.driver", marionetteDriverLocation);
        WebDriver firefoxDriver = new FirefoxDriver();
        return firefoxDriver;
    }


    //Navigate to Titan Login page
    public HomePage navigateToHomePage() {
        driver = getChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(Config.URL);
        webElementHelper.suspend(2000);
        context.setWebDriver(driver, true);
        return factory.create(HomePage.class, true);
    }

    //Navigate to Titan Login page
    public HomePage launchBrowserAndNavigateToURL() {
        driver = getChromeDriver();
        driver.navigate().to(Config.URL);
        webElementHelper.suspend(2000);
        context.setWebDriver(driver, true);
        driver.manage().window().maximize();
        return factory.create(HomePage.class, true);
    }

	/*	//Launch URL using Mobile
	public TitanMobileWelcomePage navigateToMobileTitanWelcomePage(String browserName, String platform, String platformName, String deviceName, String version) throws MalformedURLException {
		driver = getRemoteWebDriver(browserName, platform, platformName, deviceName, version);
		driver.navigate().to(Config.TITAN_URL); webElementHelper.suspend(5000);
		context.setWebDriver(driver, true);
		return factory.create(TitanMobileWelcomePage.class, true);
	}

	public AxisWelcomeView navigateToAxisMobileAPPWelcomePage(String platformName, String version, String deviceName, String browserName) throws MalformedURLException {
		driver = getRemoteWebDriverAPP(platformName, version, deviceName, browserName);
		context.setWebDriver(driver, false);
		return factory.create(AxisWelcomeView.class, true);
	}

	public TitanMobileWelcomePage navigateToMobileTitanWelcomePageFF(String browserName, String platform, String platformName, String deviceName, String version) throws MalformedURLException {
		driver = getFireFoxRemoteWebDriver(browserName, platform, platformName, deviceName, version);
		driver.navigate().to(Config.TITAN_URL); webElementHelper.suspend(5000);
		context.setWebDriver(driver, true);
		return factory.create(TitanMobileWelcomePage.class, true);*/


    public void closeBrowser() {
        driver.quit();
    }
}