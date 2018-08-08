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

import java.util.List;

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
        wait.until(ExpectedConditions.visibilityOf(getProductTitleText()));
        return getProductTitleText().getText();
    }

    private WebElement getSizeSelectionLocator() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.productDescriptionPage.dressSizeSelector));
    }

    public void selectSize(String size) {
        Select se = new Select(getSizeSelectionLocator());
        se.selectByVisibleText(size);
    }


    private WebElement getColorSelectionLocator() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.productDescriptionPage.colorSelector));
    }

    public void selectDressColor(String color) {
        WebElement ele = context.getWebDriver().findElement(By.xpath(String.format("//*[@id='color_to_pick_list']/child::li/child::a[@name ='%s']", color)));
        action.click(ele).build().perform();
    }

    private List<WebElement> getColorSelector() {
        return context.getWebDriver().findElements(By.xpath(DemoshopLocators.productDescriptionPage.colorListSelector));
    }

    public void selectDressColorFromList(int index) {
        List<WebElement> colors = getColorSelector();
        colors.get(index).click();
    }

    private WebElement getAddToCartLocator() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.productDescriptionPage.addToCartButton));

    }

    public void clickAddToCartButton() {
        getAddToCartLocator().click();
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
