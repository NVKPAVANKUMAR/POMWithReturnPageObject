package main.java.pages;


import main.java.factory.DefaultPageFactory;
import main.java.helper.CommonHelper;
import main.java.helper.WebElementHelper;
import main.java.locators.DemoshopLocators;
import main.java.pom.Page;
import main.java.util.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage extends Page {

    public DefaultPageFactory factory = new DefaultPageFactory();
    public TestContext context = TestContext.getContext();
    WebElementHelper webElementHelper = new WebElementHelper(context.getWebDriver());
    CommonHelper commonHelper = new CommonHelper(null);
    WebDriverWait wait = new WebDriverWait(context.getWebDriver(), 10);

    private WebElement getGenderSelection() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.genderRadioOption));
    }

    private WebElement getDateSelection() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.daySelector));
    }

    private WebElement getMonthSelection() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.monthSelector));
    }

    private WebElement getYearSelection() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.yearSelector));
    }

    private WebElement getFirstNameField() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.firstNameField));
    }

    private WebElement getLastNameField() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.lastNameField));
    }

    private WebElement getEmailIdField() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.emailIdField));
    }

    private WebElement getPasswordField() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.passwordField));
    }

    private WebElement getAddressFirstname() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.address_FirstName));
    }

    private WebElement getAddressLastName() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.address_LastName));
    }

    private WebElement getFullAddressField() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.full_Address));
    }

    private WebElement getCityField() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.cityName));
    }

    private WebElement getStateSelector() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.stateName));
    }

    private WebElement getZipCodeField() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.postalCode));
    }

    private WebElement getMobileNumberField() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.mobileNumber));
    }

    private WebElement getSubmitButton() {
        return context.getWebDriver().findElement(By.id(DemoshopLocators.RegistrationPage.submitButton));
    }

    public void select_Gender() {
        getGenderSelection().click();
    }

    public void input_Firstname(String fn) {
        getFirstNameField().sendKeys(fn);
    }

    public void input_Lastname(String ln) {
        getLastNameField().sendKeys(ln);

    }

    public void input_Emailid(String email) {
        getEmailIdField().sendKeys(email);
    }

    public void input_Password(String pass) {
        getPasswordField().sendKeys(pass);
    }

    public void input_DOB() {
        Select se1 = new Select(getDateSelection());
        se1.selectByValue("1");


        Select se2 = new Select(getMonthSelection());
        se2.selectByValue("1");


        Select se3 = new Select(getYearSelection());
        se3.selectByValue("2018");

    }

    public void input_Address(String fn, String ln, String add) {
        getAddressFirstname().clear();
        getAddressFirstname().sendKeys(fn);
        getAddressLastName().clear();
        getAddressLastName().sendKeys(ln);
        getFullAddressField().clear();
        getFullAddressField().sendKeys(add);

    }

    public void input_City(String city) {
        getCityField().sendKeys(city);
    }

    public void input_StateName() {
        Select se1 = new Select(getStateSelector());
        se1.selectByValue("1");

    }

    public void input_Zipcode(String pin) {
        getZipCodeField().sendKeys(pin);

    }

    public void input_MobileNumber(String num) {
        getMobileNumberField().sendKeys(num);
    }

    public MyAccountPage click_SubmitButton() {
        getSubmitButton().click();
        return factory.create(MyAccountPage.class, true);

    }

    public String getUserFullName() {
        return getFirstNameField().getAttribute("value") + " " + getLastNameField().getAttribute("value");
    }


    @Override
    public void assertIsOpen(Object... arguments) throws AssertionError {

    }
}
