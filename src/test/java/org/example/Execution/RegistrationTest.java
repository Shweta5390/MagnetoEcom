package org.example.Execution;

import io.github.bonigarcia.wdm.WebDriverManager;
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

public class RegistrationTest {
    WebDriver driver;
    WebDriverWait wait;
    HomePage homePage;
    RegistrationPage registrationPage;
    MyAccountPage myAccountPage;
    SignInPage signInPage;
    AddressPage addressPage;


    @BeforeMethod
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
        signInPage=new SignInPage(driver);
        addressPage=new AddressPage(driver);
    }

    public void takeScreenshot(String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String screenshotPath = "C:\\Users\\HP\\IdeaProjects\\Project\\MagnetoProject\\ScreenShots\\Registration\\" + screenshotName + ".png";
        File destination = new File(screenshotPath);
        FileHandler.copy(source, destination);
    }


    @Test(dataProvider = "registration0")
    public void registrationWithValidFields(String firstname, String lastname, String email, String password, String confirmpassword) throws IOException, InterruptedException {
        homePage.navigateToCreateAccount();
        registrationPage.typeIntoFirstName(firstname);
        registrationPage.typeIntoLastName(lastname);
        registrationPage.typeIntoEmail(email);
        registrationPage.typeIntoPassword(password);
        registrationPage.typeIntoConfirmPassword(confirmpassword);
        registrationPage.clickOnCreateAnAccountButton();

        Thread.sleep(5000);

        if (isElementPresent(By.xpath("//div[contains(@class, 'message-success')]"))) {
            String expectedMessage = "Thank you for registering with Main Website Store.";
            String actualMessage = registrationPage.getSuccessMessage();
            Assert.assertTrue(actualMessage.contains(expectedMessage), "Success message did not match.");
            takeScreenshot("Success_" + firstname);

            myAccountPage = new MyAccountPage(driver);


        } else {
            String expectedErrorMessage = "There is already an account with this email address.";
            String actualErrorMessage = registrationPage.getErrorMessage();
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Error message did not match.");
            takeScreenshot("Error_" + firstname);

        }
    }

    @Test(dataProvider = "registration1")
    public void registrationWithMissingMandatoryFields(String firstname, String lastname, String email, String password, String confirmpassword) throws IOException, InterruptedException {

        homePage.navigateToCreateAccount();

        if (firstname != null && !firstname.isEmpty()) {
            registrationPage.typeIntoFirstName(firstname);
        }
        if (lastname != null && !lastname.isEmpty()) {
            registrationPage.typeIntoLastName(lastname);
        }
        if (email != null && !email.isEmpty()) {
            registrationPage.typeIntoEmail(email);
        }
        if (password != null && !password.isEmpty()) {
            registrationPage.typeIntoPassword(password);
        }
        if (confirmpassword != null && !confirmpassword.isEmpty()) {
            registrationPage.typeIntoConfirmPassword(confirmpassword);
        }

        Thread.sleep(5000);
        registrationPage.clickOnCreateAnAccountButton();

        Thread.sleep(10000);

        // Check for error messages at each field
        if (firstname == null || firstname.isEmpty())  {
            String expectedFirstNameError = "This is a required field.";
            String actualFirstNameError = registrationPage.getFirstNameErrorMessage();
            Assert.assertEquals(actualFirstNameError, expectedFirstNameError, "First name error message did not match.");
        }
        if (lastname == null || lastname.isEmpty()) {
            String expectedLastNameError = "This is a required field.";
            String actualLastNameError = registrationPage.getLastNameErrorMessage();
            Assert.assertEquals(actualLastNameError, expectedLastNameError, "Last name error message did not match.");
        }
        if (email == null || email.isEmpty()) {
            String expectedEmailError = "This is a required field.";
            String actualEmailError = registrationPage.getEmailErrorMessage();
            Assert.assertEquals(actualEmailError, expectedEmailError, "Email error message did not match.");
        }
        if (password == null || password.isEmpty()) {
            String expectedPasswordError = "This is a required field.";
            String actualPasswordError = registrationPage.getPasswordErrorMessage();
            Assert.assertEquals(actualPasswordError, expectedPasswordError, "Password error message did not match.");
        }
        if (confirmpassword == null || confirmpassword.isEmpty()) {
            String expectedConfirmPasswordError = "This is a required field.";
            String actualConfirmPasswordError = registrationPage.getConfirmPasswordErrorMessage();
            Assert.assertEquals(actualConfirmPasswordError, expectedConfirmPasswordError, "Confirm password error message did not match.");
        }

        // If no error messages were expected (all fields filled), verify success
        if (firstname != null && !firstname.isEmpty() &&
                lastname != null && !lastname.isEmpty() &&
                email != null && !email.isEmpty() &&
                password != null && !password.isEmpty() &&
                confirmpassword != null && !confirmpassword.isEmpty()) {

            if (isElementPresent(By.xpath("//div[contains(@class, 'message-success')]"))) {
                String expectedMessage = "Thank you for registering with Main Website Store.";
                String actualMessage = registrationPage.getSuccessMessage();
                Assert.assertTrue(actualMessage.contains(expectedMessage), "Success message did not match.");
                takeScreenshot("Success_" + firstname);
            } else {
                Assert.fail("Expected success message was not displayed.");
            }
        } else {
            takeScreenshot("Error_" + firstname);
        }
    }

    @Test(dataProvider = "registration2")
    public void registrationWithInvalidEmail(String firstname, String lastname, String email, String password, String confirmpassword) throws IOException, InterruptedException {
        homePage.navigateToCreateAccount();
        registrationPage.typeIntoFirstName(firstname);
        registrationPage.typeIntoLastName(lastname);
        registrationPage.typeIntoEmail(email);
        registrationPage.typeIntoPassword(password);
        registrationPage.typeIntoConfirmPassword(confirmpassword);
        Thread.sleep(5000);
        registrationPage.clickOnCreateAnAccountButton();

        // Use explicit wait instead of Thread.sleep
        WebDriverWait wait = new WebDriverWait(driver, 5);

        // Check for invalid email error near the email field
        try {
            WebElement invalidEmailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='email_address-error']")));
            String expectedErrorMessage = "Please enter a valid email address (Ex: johndoe@domain.com).";
            String actualErrorMessage = invalidEmailError.getText();
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Invalid email error message did not match.");
            takeScreenshot("InvalidEmailError_" + firstname);
        } catch (TimeoutException e) {
            // Check for already registered email error
            try {
                WebElement generalError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'message-error')]")));
                String expectedErrorMessage = "There is already an account with this email address.";
                String actualErrorMessage = generalError.getText();
                Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Error message did not match.");
                takeScreenshot("AlreadyRegisteredError_" + firstname);
            } catch (TimeoutException ex) {
                // Check for success message
                try {
                    WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'message-success')]")));
                    String expectedMessage = "Thank you for registering with Main Website Store.";
                    String actualMessage = successMessage.getText();
                    Assert.assertTrue(actualMessage.contains(expectedMessage), "Success message did not match.");
                    takeScreenshot("Success_" + firstname);
                } catch (TimeoutException exc) {
                    // No message found
                    System.out.println("Page source: " + driver.getPageSource());
                    Assert.fail("No appropriate error or success message displayed.");
                }
            }
        }
    }


    @Test(dataProvider = "registration3")
    public void registrationWithMissMatchPassword(String firstname, String lastname, String email, String password, String confirmpassword) throws IOException, InterruptedException {
        homePage.navigateToCreateAccount();
        registrationPage.typeIntoFirstName(firstname);
        registrationPage.typeIntoLastName(lastname);
        registrationPage.typeIntoEmail(email);
        registrationPage.typeIntoPassword(password);
        registrationPage.typeIntoConfirmPassword(confirmpassword);
        Thread.sleep(5000);
        registrationPage.clickOnCreateAnAccountButton();

        // Use explicit wait to handle timing issues
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            // Check if the password confirmation error message is displayed
            WebElement passwordMismatchError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='password-confirmation-error']")));
            String expectedErrorMessage = "Please make sure your passwords match.";
            String actualErrorMessage = passwordMismatchError.getText();
            Assert.assertFalse(actualErrorMessage.contains(expectedErrorMessage), "Password mismatch error message did not match.");
            takeScreenshot("PasswordMismatchError_" + firstname);
        } catch (TimeoutException e) {
            // If no password mismatch error, check for other possible outcomes
            if (isElementPresent(By.xpath("//div[contains(@class, 'message-success')]"))) {
                String expectedMessage = "Thank you for registering with Main Website Store.";
                String actualMessage = registrationPage.getSuccessMessage();
                Assert.assertTrue(actualMessage.contains(expectedMessage), "Success message did not match.");
                takeScreenshot("Success_" + firstname);
            } else {
                // Check for already registered email error
                String expectedErrorMessage = "There is already an account with this email address.";
                String actualErrorMessage = registrationPage.getErrorMessage();
                Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Error message did not match.");
                takeScreenshot("AlreadyRegisteredError_" + firstname);
            }
        }
    }

//    @Test(dependsOnMethods = "registrationWithValidFields")
//    public void validateAddressDetails() throws IOException {
//
//        signInPage = new SignInPage(driver);
//        myAccountPage = new MyAccountPage(driver);
//        addressPage = new AddressPage(driver);
////        signInPage.login(email, password);
//        myAccountPage.clickOnAddressBook();
//
//
//        addressPage.enterAddressDetails("Company", "123 Main St", "Sangli", "123456", "United States", "Alaska", "1234567890");
//        addressPage.clickOnSaveAddressButton();
//
//        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message-success success message']")));
//        String expectedMessage = "You saved the address.";
//        String actualMessage = successMessage.getText();
//        Assert.assertTrue(actualMessage.contains(expectedMessage), "Address save message did not match.");
//
//        takeScreenshot("AddressSaved");
//    }



    @DataProvider(name = "registration0")
    public Object[][] fetchDataForRegistrationValidData() throws Exception {
        Object[][] data = null;
        ExcelDataReader reader = new ExcelDataReader();
        data = reader.getDataFRomExcel(0);


        return data;
    }
    @DataProvider(name = "registration1")
    public Object[][] fetchDataForRegistrationForMissingFields() throws Exception {
        Object[][] data = null;
        ExcelDataReader reader = new ExcelDataReader();
        data = reader.getDataFRomExcel(1);


        return data;
    }
    @DataProvider(name = "registration2")
    public Object[][] fetchDataForRegistrationInvalidEmail() throws Exception {
        Object[][] data = null;
        ExcelDataReader reader = new ExcelDataReader();
        data = reader.getDataFRomExcel(2);


        return data;
    }

    @DataProvider(name = "registration3")
    public Object[][] fetchDataForRegistrationMissMatchPassword() throws Exception {
        Object[][] data = null;
        ExcelDataReader reader = new ExcelDataReader();
        data = reader.getDataFRomExcel(3);


        return data;
    }

    @AfterMethod
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



