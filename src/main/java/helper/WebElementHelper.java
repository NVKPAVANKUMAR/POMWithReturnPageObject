package main.java.helper;

import main.java.util.TestContext;
import main.java.web.ExtentBase;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class WebElementHelper {
    WebDriver driver;
    WebElement e = null;

    public WebElementHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElementToAppear(String selector) {
        long end = System.currentTimeMillis() + 30000;
        WebElement element = null;
        boolean nseLogMessage = true;
        while (System.currentTimeMillis() < end) {
            try {
                if (selector.contains("//")) {
                    element = driver.findElement(By.xpath(selector));
                } else if (selector.substring(0, 1).contains("#") || selector.substring(0, 1).contains(".") || selector.contains(">")) {
                    element = driver.findElement(By.cssSelector(selector));
                } else if (!selector.contains("//")) {
                    element = driver.findElement(By.id(selector));
                }
                if (element == null) {
                    this.suspend(3000);
                    System.out
                            .println("Waiting for " + selector + " to appear");
                } else {
                    if (element != null) {
                        break;
                    }
                }
            } catch (NoSuchElementException nse) {
                if (nseLogMessage) {
                    this.suspend(3000);
                    System.out
                            .println("Waiting for " + selector + " to appear");
                    nseLogMessage = false;
                }
            }

        }
        if (element == null) {
            try {
                WebElement errorMessage = TestContext.getContext().getWebDriver().findElement(By.id("errorsDiv"));
                if (errorMessage.isDisplayed()) {
                    Assert.fail("Test case failed because of " + errorMessage.getText() + ", Please try executing in different build");
                }
            } catch (NoSuchElementException ex1) {
                Assert.fail("Unable to find  expected WebElement before timeout.");
            }
        }
    }


    public void suspend(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // To click on the element using java script
    public boolean jScriptClickElement(WebDriver driver, WebElement ele) {
        boolean ans = true;
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", ele);

        } catch (Exception e) {
            ele.click();
            e.printStackTrace();
        }
        return ans;
    }

    // To select the element using drop down by visible text
    public void selectComboBoxByVisibleText(WebDriver driver, WebElement ele,
                                            String selectvalue) {
        Select obj = new Select(ele);
        try {
            ele.click();
            Thread.sleep(1000);
            //ExtentBase.captureWindowScreenshot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            obj.selectByVisibleText(selectvalue);
        } catch (NoSuchElementException ex) {
            Assert.fail("TC failed: Value -- " + selectvalue + "  -- is not present in the dropdown list");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // To select the element using drop down by visible text
    public void selectComboBoxByVisibleText1(WebElement ele,
                                             String selectvalue) {
        try {
            Select obj = new Select(ele);
            obj.selectByVisibleText(selectvalue);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // To select the element using drop down by index
    public void SelectComboBoxByIndex(WebDriver driver, WebElement ele,
                                      int index) {
        try {
            Select obj = new Select(ele);
            obj.selectByIndex(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // To handle the pop up
    public void acceptTheAlertWindow(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    public String getTheAlertWindowText(WebDriver driver) {
        String alertText = driver.switchTo().alert().getText();
        return alertText;
    }

    // Click on the element using action class
    public void actionClick(WebDriver driver, WebElement ele) {
        Actions builder = new Actions(driver);
        try {
            builder.moveToElement(ele).click(ele).build().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Verifies the element is present or not
    public boolean verifyElementIsPresent(WebDriver driver, WebElement ele) {
        boolean elementPresent = false;
        try {
            elementPresent = ele.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementPresent;
    }

    // Get the text
    public String getText(WebDriver driver, WebElement ele) {
        String returnText = null;
        try {
            returnText = ele.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnText;
    }

    // To mouse hover on the element using action class
    public void mouseHoverOnElement(WebDriver driver, WebElement ele) {
        Actions action = new Actions(driver);
        action.moveToElement(ele).build().perform();
    }

    // To mouse hover on the element using java script
    public void mouseHoverJScript(WebDriver driver, WebElement HoverElement) {
        try {
            if (verifyElementIsPresent(driver, HoverElement)) {

                String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
                ((JavascriptExecutor) driver).executeScript(mouseOverScript,
                        HoverElement);

            } else {
                ExtentBase.addComment("Element was not visible to hover " + "\n");

            }
        } catch (StaleElementReferenceException e) {
            ExtentBase.addComment("Element with " + HoverElement
                    + "is not attached to the page document"
                    + e.getStackTrace());
        } catch (NoSuchElementException e) {
            ExtentBase.addComment("Element " + HoverElement
                    + " was not found in DOM" + e.getStackTrace());
        } catch (Exception e) {
            e.printStackTrace();
            ExtentBase.addComment("Error occurred while hovering"
                    + e.getStackTrace());
        }
    }

    // Verifies the element is selected or not
    public boolean isSelected(WebDriver driver, WebElement ele) {
        boolean status = false;
        try {
            status = ele.isSelected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // Forces the page to scroll up
    public void scrollUp(WebDriver driver) {
        try {
            driver.findElement(By.tagName("body")).sendKeys(Keys.HOME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Forces the page to scroll up
    public void scrollDown(WebDriver driver) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,"
                + "document.body.scrollHeight,document.documentElement.clientHeight));");

    }

    // Forces the page to scroll up
    public void scrollToElement(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:window.scrollBy(0,100)");
    }

    // Method to save a tool tip value in a variable
    public String getToolTipValue(String attribute, String locator) {
        String toolTipValue = "";
        try {
            toolTipValue = driver.findElement(By.xpath(locator)).getAttribute(
                    attribute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toolTipValue;

    }


    public void pageDown(WebDriver driver) {
        try {
            driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Click on the element
    public void clickOnElement(WebDriver driver, WebElement ele) {
        try {
            ele.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getHostAndPin(WebDriver driver, WebElement ele) {
        String getHostPin = ele.getText();
        getHostPin = getHostPin.substring(getHostPin.lastIndexOf("Host - "),
                getHostPin.lastIndexOf(", PVT"));
        String getHost = getHostPin.substring(getHostPin.lastIndexOf(""),
                getHostPin.lastIndexOf(":"));
        String getPin = getHostPin.substring(getHostPin.lastIndexOf(":"),
                getHostPin.lastIndexOf(""));
        String userHostAndPin = "Host: " + getHost + "Pin: " + getPin;
        return userHostAndPin;
    }

    public void writeText(WebElement textField, String text) {

        String js;
        JavascriptExecutor exec = (JavascriptExecutor) driver;

        js = String.format("arguments[0].value='%s';", text);
        exec.executeScript(js, textField);
    }
}
