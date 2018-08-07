package main.java.locators;

import com.sun.javafx.binding.StringFormatter;

public class DemoshopLocators {

    public class HomePage {
        public static final String homePageLogo = "//*[@class='logo img-responsive']";
        public static final String signInButton = "//*[@class='login']";
        public static final String womenSelector = "//*[.='Women']";
        public static final String dressesSelector = "//*[.='Dresses']";
    }

    public class SignUpPage {
        public static final String emailIdfield = "email_create";
        public static final String createAccountButton = "SubmitCreate";
    }

    public class RegistrationPage {
        public static final String genderRadioOption = "uniform-id_gender1";
        public static final String firstNameField = "customer_firstname";
        public static final String lastNameField = "customer_lastname";
        public static final String emailIdField = "email";
        public static final String passwordField = "passwd";
        public static final String daySelector = "days";
        public static final String monthSelector = "months";
        public static final String yearSelector = "years";
        public static final String signInButton = "//*[@class='login']";
        public static final String address_FirstName = "firstname";
        public static final String address_LastName = "lastname";
        public static final String address_Company = "company";
        public static final String full_Address = "address1";
        public static final String cityName = "city";
        public static final String stateName = "id_state";
        public static final String postalCode = "postcode";
        public static final String mobileNumber = "phone_mobile";
        public static final String submitButton = "submitAccount";
    }

    public class MyAccountPage {
        public static final String userName = "//*[@id='header']/div[2]/div/div/nav/div[1]/a/span";
    }

    public class dressesDisplayPage {
        public static final String productAvailabilityText = "//*[@class=\"heading-counter\"]";
        public static final String productLocator = "//*[@id='center_column']/ul/li";
        public static final String dressSelector = "//*[@id=\"center_column\"]/ul/li[3]/div/div[2]/h5/a";
    }

    public class productDescriptionPage {
        public static final String productText = "//*[@id=\"center_column\"]/div/div/div[3]/h1";
        public static final String dressSizeSelector = "group_1";
        public static final String colorSelector = "color_14";
        public static final String colorListSelector = "//*[@id=\"color_to_pick_list\"]/li";
        public static final String addToCartButton = "add_to_cart";
        public static final String cartSummaryLayer = "layer_cart";
        public static final String proceedToCheckoutButton = "//*[@id='layer_cart']/div[1]/div[2]/div[4]/a";

    }

    public class cartPage {
        public static final String productSpecification = "//*[@id='product_5_24_0_0']/td[2]/small[2]/a";
    }

}
