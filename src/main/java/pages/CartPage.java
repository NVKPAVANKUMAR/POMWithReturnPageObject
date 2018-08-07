package main.java.pages;

import main.java.factory.DefaultPageFactory;
import main.java.helper.CommonHelper;
import main.java.helper.WebElementHelper;
import main.java.locators.DemoshopLocators;
import main.java.pom.Page;
import main.java.util.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CartPage extends Page {

    public DefaultPageFactory factory = new DefaultPageFactory();
    public TestContext context = TestContext.getContext();
    WebElementHelper webElementHelper = new WebElementHelper(context.getWebDriver());
    CommonHelper commonHelper = new CommonHelper(null);
    Actions action = new Actions(context.getWebDriver());
    JavascriptExecutor executor = ((JavascriptExecutor) context.getWebDriver());

    private WebElement getAddedProductSpecification() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.cartPage.productSpecification));
    }

    public String textToAssert() {
        executor.executeScript("arguments[0].scrollIntoView(true);", getAddedProductSpecification());
        return getAddedProductSpecification().getText();
    }


    @Override
    public void assertIsOpen(Object... arguments) throws AssertionError {

    }
}
