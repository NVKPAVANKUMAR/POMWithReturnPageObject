package main.java.pages;

import main.java.factory.DefaultPageFactory;
import main.java.helper.CommonHelper;
import main.java.helper.WebElementHelper;
import main.java.locators.DemoshopLocators;
import main.java.pom.Page;
import main.java.util.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MyAccountPage extends Page {


    public DefaultPageFactory factory = new DefaultPageFactory();
    public TestContext context = TestContext.getContext();
    WebElementHelper webElementHelper = new WebElementHelper(context.getWebDriver());
    CommonHelper commonHelper = new CommonHelper(null);

    private WebElement parseUserName() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.MyAccountPage.userName));
    }

    public String getUserName() {
        return parseUserName().getText();
    }

    @Override
    public void assertIsOpen(Object... arguments) throws AssertionError {

    }
}
