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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDescriptionPage extends Page {


    public DefaultPageFactory factory = new DefaultPageFactory();
    public TestContext context = TestContext.getContext();
    WebElementHelper webElementHelper = new WebElementHelper(context.getWebDriver());
    CommonHelper commonHelper = new CommonHelper(null);
    Actions action = new Actions(context.getWebDriver());
    JavascriptExecutor executor = ((JavascriptExecutor) context.getWebDriver());
    WebDriverWait wait = new WebDriverWait(context.getWebDriver(), 10);

    private WebElement getProductTitleText() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.productDescriptionPage.productText));
    }

    public String parseProductTitleText() {
        return getProductTitleText().getText();
    }

    private WebElement getSizeSelectionLocator() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.productDescriptionPage.dressSizeSelector));
    }

    public void selectSize() {
        Select se = new Select(getSizeSelectionLocator());
        se.selectByVisibleText("M");
    }


    private WebElement getColorSelectionLocator() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.productDescriptionPage.colorSelector));
    }

    public void selectDressColor() {
        getColorSelectionLocator().click();
    }

    private WebElement getAddToCartLocator() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.productDescriptionPage.addToCartButton));

    }

    public void clickAddToCartButton() {
        getAddToCartLocator().click();
    }

    private WebElement getCartSummaryLayer() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.productDescriptionPage.cartSummaryLayer));

    }

    private WebElement getProceedToCheckoutLocator() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.productDescriptionPage.proceedToCheckoutButton));

    }

    public CartPage clickOnProceedToCheckout() {
        wait.until(ExpectedConditions.visibilityOf(getProceedToCheckoutLocator()));
        getProceedToCheckoutLocator().click();
        return factory.create(CartPage.class, true);
    }

    @Override
    public void assertIsOpen(Object... arguments) throws AssertionError {

    }
}
