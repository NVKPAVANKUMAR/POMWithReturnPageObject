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
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage extends Page {

    public DefaultPageFactory factory = new DefaultPageFactory();
    public TestContext context = TestContext.getContext();
    WebElementHelper webElementHelper = new WebElementHelper(context.getWebDriver());
    CommonHelper commonHelper = new CommonHelper(null);
    Actions action = new Actions(context.getWebDriver());
    JavascriptExecutor executor = ((JavascriptExecutor) context.getWebDriver());
    WebDriverWait wait = new WebDriverWait(context.getWebDriver(), 10);

    private WebElement getHomePageLogo() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.HomePage.homePageLogo));
    }

    private WebElement getSignUpLink() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.HomePage.signInButton));
    }

    private WebElement getWomenSelector() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.HomePage.womenSelector));
    }

    private WebElement getWomenDressSelector() {
        return context.getWebDriver().findElement(By.xpath(DemoshopLocators.HomePage.dressesSelector));
    }

    public void hoverOnWomenSelector() {
        wait.until(ExpectedConditions.visibilityOf(getWomenSelector()));
        action.moveToElement(getWomenSelector()).build().perform();
    }

    public DressesPage clickOnWomenDressSelector() {
        wait.until(ExpectedConditions.visibilityOf(getWomenDressSelector()));
        action.moveToElement(getWomenDressSelector()).click().build().perform();
        return factory.create(DressesPage.class, true);
    }

    public boolean isLogoDisplayed() {
        return getHomePageLogo().isDisplayed();
    }

    public SignUpPage clickOnSignUp() {
        getSignUpLink().click();
        return factory.create(SignUpPage.class, true);
    }


    @Override
    public void assertIsOpen(Object... arguments) throws AssertionError {

    }
}
