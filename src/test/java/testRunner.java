package test.java;

import com.arakelian.faker.service.RandomAddress;
import com.arakelian.faker.service.RandomPerson;
import main.java.config.Config;
import main.java.helper.Browser;
import main.java.helper.WebElementHelper;
import main.java.pages.*;
import main.java.util.RandomEmailGenerator;
import main.java.util.RandomPasswordGenerator;
import main.java.util.ReportGenerator;
import main.java.web.ExtentBase;
import org.apache.velocity.test.provider.Person;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static main.java.util.ReportGenerator.*;


public class testRunner extends ExtentBase {
    WebElementHelper webElementHelper = new WebElementHelper(null);
    Browser appLaunch = new Browser();
    Config config = new Config();
    Person person = new Person();
    private HomePage homePage;
    private SignUpPage signUpPage;
    private RegistrationPage registrationPage;
    private MyAccountPage myAccountPage;
    private DressesPage dressesPage;
    private ProductDescriptionPage productDescriptionPage;
    private CartPage cartPage;

    @BeforeSuite
    public void setUp() {
        startReport();
    }

    @Test
    public void test_01_SignUp() throws InterruptedException {
        startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        homePage = appLaunch.navigateToHomePage();
        assert homePage.isLogoDisplayed() == true;
        signUpPage = homePage.clickOnSignUp();
        assert signUpPage.isCreateButtonExist() == true;
        signUpPage.input_Email(RandomEmailGenerator.generate());
        registrationPage = signUpPage.click_createAccountButton();
        Thread.sleep(3000);
        registrationPage.select_Gender();
        registrationPage.input_Firstname(RandomPerson.get().next().getFirstName());
        registrationPage.input_Lastname(RandomPerson.get().next().getLastName());
        String initialName = registrationPage.getUserFullName();
        registrationPage.input_Password(RandomPasswordGenerator.generate());
        registrationPage.input_DOB();
        registrationPage.input_Address(RandomPerson.get().next().getFirstName()
                , RandomPerson.get().next().getLastName(), "688, Sunnyslope Avenue,Lakewood,NJ 08701");
        registrationPage.input_City(RandomAddress.get().next().getCity());
        registrationPage.input_StateName();
        registrationPage.input_Zipcode(RandomAddress.get().next().getPostalCode());
        registrationPage.input_MobileNumber("9848012345");
        myAccountPage = registrationPage.click_SubmitButton();
        assert myAccountPage.getUserName().equalsIgnoreCase(initialName);
        ReportGenerator.logger.pass("SingUp TestCase Passed");
    }

    @Test
    public void test_02_OrderDress() throws InterruptedException {
        startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        homePage = appLaunch.navigateToHomePage();
        assert homePage.isLogoDisplayed() == true;
        homePage.hoverOnWomenSelector();
        dressesPage = homePage.clickOnWomenDressSelector();
        assert dressesPage.textToAssert().equalsIgnoreCase(config.VERIFICATION_TEXT);
        productDescriptionPage = dressesPage.clickOnProduct(2);
        assert productDescriptionPage.parseProductTitleText().equalsIgnoreCase(config.PRODUCT_VERIFICATION_TEXT);
        productDescriptionPage.selectSize("M");
        productDescriptionPage.selectDressColorFromList(2);
        productDescriptionPage.clickAddToCartButton();
        cartPage = productDescriptionPage.clickOnProceedToCheckout();
        assert cartPage.textToAssert().equalsIgnoreCase(config.CART_SPECIFICATION_TEXT);
        ReportGenerator.logger.pass("Order Dress TestCase Passed");
    }

    @AfterMethod
    public void closeBrowser() {
        appLaunch.closeBrowser();
    }

    @AfterClass
    public void tearDown() {
        endReport();
    }

}
