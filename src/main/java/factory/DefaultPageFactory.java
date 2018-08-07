package main.java.factory;

import main.java.pom.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.FieldDecorator;


public class DefaultPageFactory implements PageFactory {
    @Override
    public <T extends Page> T create(Class<T> pageClass, boolean waitForPageLoad, FieldDecorator decorator, Object... arguments) throws AssertionError {
        return createInstanceOf(pageClass, waitForPageLoad, decorator, null, arguments);
    }

    @Override
    public <T extends Page> T create(Class<T> pageClass, boolean waitForPageLoad, WebDriver driver, Object... arguments) throws AssertionError {
        return createInstanceOf(pageClass, waitForPageLoad, null, driver, arguments);
    }

    @Override
    public <T extends Page> T create(Class<T> pageClass, boolean waitForPageLoad, Object... arguments) throws AssertionError {
        return createInstanceOf(pageClass, waitForPageLoad, null, null, arguments);
    }

    private <T extends Page> T createInstanceOf(final Class<T> pageClass, boolean waitForPageLoad, FieldDecorator decorator, WebDriver driver, Object... arguments) throws AssertionError {
        try {
            T page = pageClass.newInstance();

            if (decorator == null && driver == null) {
                page.initElements();
            } else if (decorator != null && driver == null) {
                page.initElements(decorator);
            } else if (decorator == null && driver != null) {
                page.initElements(driver);
            }

            if (waitForPageLoad) {
                page.assertIsOpen(arguments);
            }

            return page;
        } catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
