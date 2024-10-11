package org.example.Execution;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.Pages.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteProductAndCheckoutTest {
        WebDriver driver;
        WebDriverWait wait;
        SignInPage signInPage;
        HomePage homePage;
        ProductPage productPage;
        CartPage cartPage;
        CheckoutPage checkoutPage;

        @BeforeClass
        public void launchBrowser() {
            WebDriverManager.firefoxdriver().setup();
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
            driver = new FirefoxDriver();

            driver.get("https://magento.softwaretestingboard.com/");
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, 30);

            // Initialize page objects
            homePage = new HomePage(driver);
            signInPage = new SignInPage(driver);
            productPage = new ProductPage(driver,wait);
            cartPage = new CartPage(driver, wait);
            checkoutPage = new CheckoutPage(driver, wait);
        }
        public void takeScreenshot(String screenshotName) throws IOException {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String screenshotPath = "C:\\Users\\HP\\IdeaProjects\\Project\\MagnetoProject\\ScreenShots\\CheckOut\\" + screenshotName + ".png";
            File destination = new File(screenshotPath);
            FileHandler.copy(source, destination);
        }


    @Test
    public void deleteProductAndCheckoutAgain() throws InterruptedException, IOException {
//            cartPage.clickOnContinue();
        homePage.navigateToSignIn();
        signInPage.signIn("Arav.Surya241@example.com", "Password2");
        // Add products to the cart
        homePage.selectProduct("Radiant Tee");
        Thread.sleep(5000);
        productPage.selectSize("M");
        productPage.selectColor("Blue");
        productPage.addToCart();
        Thread.sleep(3000);
        driver.navigate().back();
        Thread.sleep(2000);
        homePage.selectProduct("Push It Messenger Bag");
        Thread.sleep(5000);
        productPage.addToCart();
        Thread.sleep(3000);
        driver.navigate().back();
        Thread.sleep(2000);
        homePage.selectProduct("Fusion Backpack");
        Thread.sleep(2000);
        productPage.addToCart();
        Thread.sleep(3000);
        driver.navigate().back();
        Thread.sleep(2000);
        // Navigate to cart page and delete one product
        productPage.clickOnShowCart();
        takeScreenshot("BeforeDelete");

        cartPage.deleteProduct("Radiant Tee");
        Thread.sleep(5000);
        productPage.clickOnAcceptButton();
        Thread.sleep(3000);
//        productPage.clickOnShowCart();
        takeScreenshot("AfterDelete");
        // Proceed to checkout with the remaining product
        cartPage.proceedToCheckout();
//            checkoutPage.fillBillingDetails("Arav", "Suryawanshi", "123 Street", "Sangli", "123456", "United States", "Alaska", "9876541320");
        cartPage.clickOnPlaceOrderButton();

        int orderNumber = cartPage.getNumberOfItemsInCart();
        System.out.println("Order Number: " + orderNumber);

        Assert.assertNotNull(orderNumber, "Order number should not be null, checkout failed.");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

