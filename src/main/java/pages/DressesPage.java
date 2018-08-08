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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DressesPage extends Page {
    public DefaultPageFactory factory = new DefaultPageFactory();
    public TestContext context = TestContext.getContext();
    WebElementHelper webElementHelper = new WebElementHelper(context.getWebDriver());
    CommonHelper commonHelper = new CommonHelper(null);
    Actions action = new Actions(context.getWebDriver());
    JavascriptExecutor executor = ((JavascriptExecutor) context.getWebDriver());
    List<WebElement> dressList;

    private WebElement getAssertionText() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.dressesDisplayPage.productAvailabilityText));
    }

    public String textToAssert() {
        executor.executeScript("arguments[0].scrollIntoView(true);", getAssertionText());
        return getAssertionText().getText();
    }

    private WebElement getDressLocator() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.dressesDisplayPage.dressSelector));
    }

    public ProductDescriptionPage clickOnDress() {
        //getDressLocator().click();
        action.moveToElement(getDressLocator()).click().build().perform();
        return factory.create(ProductDescriptionPage.class, true);
    }

    private List<WebElement> getProductLocator() {
        context.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return context.getWebDriver().findElements(By.xpath(DemoshopLocators.dressesDisplayPage.productLocator));
    }

    public ProductDescriptionPage clickOnProduct(int index) {
        List<WebElement> list = getProductLocator();
        action.click(list.get(index)).build().perform();
        return factory.create(ProductDescriptionPage.class, true);
    }


    @Override
    public void assertIsOpen(Object... arguments) throws AssertionError {

    }
}
