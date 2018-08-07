package main.java.pages;


import main.java.factory.DefaultPageFactory;
import main.java.helper.CommonHelper;
import main.java.helper.WebElementHelper;
import main.java.locators.DemoshopLocators;
import main.java.pom.Page;
import main.java.util.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SignUpPage extends Page {

    public DefaultPageFactory factory = new DefaultPageFactory();
    public TestContext context = TestContext.getContext();
    WebElementHelper webElementHelper = new WebElementHelper(context.getWebDriver());
    CommonHelper commonHelper = new CommonHelper(null);

    private WebElement getEmailIdField() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.SignUpPage.emailIdfield));
    }

    private WebElement getCreateAccountButton() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.SignUpPage.createAccountButton));
    }

    public void input_Email(String mailId) {
        getEmailIdField().sendKeys(mailId);

    }

    public boolean isCreateButtonExist() {
        return getCreateAccountButton().isDisplayed();
    }

    public RegistrationPage click_createAccountButton() {
        getCreateAccountButton().click();
        return factory.create(RegistrationPage.class, true);
    }


    @Override
    public void assertIsOpen(Object... arguments) throws AssertionError {

    }
}