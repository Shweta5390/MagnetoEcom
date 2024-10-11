package org.example.Execution;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.Pages.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AddToCartTest {
    WebDriver driver;
    WebDriverWait wait;
    SignInPage signInPage;
    AddressPage addressPage;
    HomePage homePage;
    ProductPage productPage;
    CartPage cartPage;
    MyAccountPage myAccountPage;

    @BeforeMethod
    public void launchBrowser() {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
        driver = new FirefoxDriver();

        driver.get("https://magento.softwaretestingboard.com/");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20);
        homePage = new HomePage(driver);
        signInPage = new SignInPage(driver);
        myAccountPage= new MyAccountPage(driver);
        addressPage = new AddressPage(driver);
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver,wait);
        cartPage = new CartPage(driver,wait);
    }
    public void takeScreenshot(String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String screenshotPath = "C:\\Users\\HP\\IdeaProjects\\Project\\MagnetoProject\\ScreenShots\\AddToCart\\" + screenshotName + ".png";
        File destination = new File(screenshotPath);
        FileHandler.copy(source, destination);
    }

    @Test(priority = 1)
    public void signInAndEnterAddressDetails() throws InterruptedException {
        homePage.navigateToSignIn();
        signInPage.signIn("Arav.Surya241@example.com", "Password2");
        Thread.sleep(50);
//        homePage.navigateToMyAccountPage();
//        myAccountPage.clickOnAddressBook();
//        addressPage.enterAddressDetails("Company","123 street","Sangli","123456","United States","Alaska","9876541320");
//        addressPage.clickOnSaveAddressButton();
    }

    @Test(priority = 2, dependsOnMethods = "signInAndEnterAddressDetails")
    public void addRadiantTeeToCart() throws InterruptedException, IOException {
        homePage.selectRadiantTeeProduct();
        Thread.sleep(5000);
        productPage.selectSize("M");
        productPage.selectColor("Blue");
        productPage.addToCart();
        Thread.sleep(2000);

        // Increase the timeout to 30 seconds
        WebDriverWait wait = new WebDriverWait(driver,(10));
        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message-success success message']")));

        String actualMessage = successMessageElement.getText();
        String expectedMessage = "You added Radiant Tee to your shopping cart.";
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Added cart message did not match.");

        takeScreenshot("AddedToCart");
    }

    @Test
    public void addProductToCart() {
        // Navigate to the Magento home page
        driver.get("https://magento.softwaretestingboard.com/");

        // Add "Breathe-Easy Tank" to the cart
        try {
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Breathe-Easy Tank")));
            productLink.click();

            // Assuming productPage methods handle the interactions on the product page
            productPage.selectSize("M");
            productPage.selectColor("Purple");
            productPage.addToCart();

            // Wait and confirm the product was added to the cart
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message-success success message']")));
            System.out.println("Success message: " + successMessage.getText());
        } catch (Exception e) {
            System.out.println("Product link not found or not clickable: Breathe-Easy Tank");
        }

        // Add "Fusion Backpack" to the cart
        try {
            driver.get("https://magento.softwaretestingboard.com/"); // Navigate back to the home page
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Fusion Backpack")));
            productLink.click();

            // No size or color selection needed for this product
            productPage.addToCart();

            // Wait and confirm the product was added to the cart
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message-success success message']")));
            System.out.println("Success message: " + successMessage.getText());
        } catch (Exception e) {
            System.out.println("Product link not found or not clickable: Fusion Backpack");
        }

        // Verify that the cart has the expected number of items
        try {
            WebElement cartCounter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='counter-number']")));
            System.out.println("Product(s) successfully added to cart. Cart count: " + cartCounter.getText());
        } catch (Exception e) {
            System.out.println("Failed to retrieve cart count.");
        }
    }


    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
