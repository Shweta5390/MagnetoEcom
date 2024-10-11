package org.example.Execution.RegistartionTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.Execution.ExcelDataReader;
import org.example.Pages.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationWithValidDataTest {
    WebDriver driver;
    WebDriverWait wait;
    HomePage homePage;
    RegistrationPage registrationPage;
    MyAccountPage myAccountPage;
    SignInPage signInPage;
    AddressPage addressPage;

    @BeforeClass
    public void launchBrowser() {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
        driver = new FirefoxDriver();

        driver.get("https://magento.softwaretestingboard.com/");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        homePage = new HomePage(driver);
        registrationPage = new RegistrationPage(driver, wait);
        signInPage = new SignInPage(driver);
        addressPage = new AddressPage(driver);
        myAccountPage = new MyAccountPage(driver);
    }

    public void takeScreenshot(String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String screenshotPath = "C:\\Users\\HP\\IdeaProjects\\Project\\MagnetoProject\\ScreenShots\\Registration\\" + screenshotName + ".png";
        File destination = new File(screenshotPath);
        FileHandler.copy(source, destination);
    }

//    @Test(dataProvider = "registrationData")
//    public void registrationWithValidFields(String firstname, String lastname, String email, String password, String confirmpassword) throws IOException, InterruptedException {
//        homePage.navigateToCreateAccount();
//        registrationPage.typeIntoFirstName(firstname);
//        registrationPage.typeIntoLastName(lastname);
//        registrationPage.typeIntoEmail(email);
//        registrationPage.typeIntoPassword(password);
//        registrationPage.typeIntoConfirmPassword(confirmpassword);
//        registrationPage.clickOnCreateAnAccountButton();
//
//        Thread.sleep(5000); // Try using WebDriverWait to avoid explicit wait
//
//        if (isElementPresent(By.xpath("//div[contains(@class, 'message-success')]"))) {
//            String expectedMessage = "Thank you for registering with Main Website Store.";
//            String actualMessage = registrationPage.getSuccessMessage();
//            Assert.assertTrue(actualMessage.contains(expectedMessage), "Success message did not match.");
//            takeScreenshot("Success_" + firstname);
//
//            // Validate address details
//            myAccountPage.clickOnAddressBook();
//            validateAddressDetails();
//        } else {
//            String expectedErrorMessage = "There is already an account with this email address.";
//            String actualErrorMessage = registrationPage.getErrorMessage();
//            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Error message did not match.");
//            takeScreenshot("Error_" + firstname);
//        }
//    }

    @Test
    public void validateAddressDetails() throws IOException, InterruptedException {
        homePage.navigateToSignIn();
        signInPage.clearFields();
        Thread.sleep(5000);
        signInPage.loginUser("Arav.Surya105@example.com", "Password2");
//        boolean isWelcomeMessageDisplayed = signInPage.isWelcomeMessageDisplayed();
//        boolean isErrorMessageDisplayed = signInPage.isErrorMessageDisplayed();
        Thread.sleep(6000);
        myAccountPage.clickOnArrowLink();
        Thread.sleep(5000);
        myAccountPage.goToMyAccount();
//        signInPage.loginUser("Arav.Surya100@example.com", "Password2");

        myAccountPage.clickOnAddressBook();
        Thread.sleep(10000);

        // Enter address details
        addressPage.enterAddressDetails("Company", "123 Main St", "Sangli", "123456", "United States", "Alaska", "1234567890");
        addressPage.clickOnSaveAddressButton();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message-success success message']")));
        String expectedMessage = "You saved the address.";
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Address save message did not match.");

        takeScreenshot("AddressSaved");
    }

    @DataProvider(name = "registrationData")
    public Object[][] fetchDataForRegistrationValidData() throws Exception {
        ExcelDataReader reader = new ExcelDataReader();
        return reader.getDataFRomExcel(4);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
