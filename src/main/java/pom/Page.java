package main.java.pom;


import com.github.webdriverextensions.WebPage;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebElement;

public abstract class Page extends WebPage {
    public WebElement webElementException = null;
    public String stringException = null;

    public static void addComment(String loggerInfo) {

        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Page.class);
        logger.setLevel(Level.DEBUG);
        logger.debug(loggerInfo);
    }

    @Override
    public void open(Object... arguments) {
    }


}
