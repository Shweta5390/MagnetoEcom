package org.example.Execution;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.Pages.HomePage;
import org.example.Pages.MyAccountPage;
import org.example.Pages.SignInPage;
import org.example.Pages.RegistrationPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class SignInTest {
        WebDriver driver;
        SignInPage signInPage;
        WebDriverWait wait;
        HomePage homePage;
        RegistrationPage registrationPage;
        MyAccountPage myAccountPage;


        @BeforeMethod
        public void launchBrowser() {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.get("https://magento.softwaretestingboard.com/");
            wait = new WebDriverWait(driver, 10);
            homePage = new HomePage(driver);
            registrationPage = new RegistrationPage(driver,wait);
            signInPage = new SignInPage(driver);
            myAccountPage= new MyAccountPage(driver);
        }
    public void takeScreenshot(String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String screenshotPath = "C:\\Users\\HP\\IdeaProjects\\Project\\MagnetoProject\\ScreenShots\\LogIn\\" + screenshotName + ".png";
        File destination = new File(screenshotPath);
        FileHandler.copy(source, destination);
    }

        @Test
        public void testLogin() {
            homePage.navigateToSignIn();
            signInPage.loginUser("username@example.com", "password");
        }

        @Test
        public void testValidLogin() throws InterruptedException, IOException {
            homePage.navigateToSignIn();
            signInPage.clearFields();
            Thread.sleep(5000);
            signInPage.loginUser("Shweta@gmail.com", "Shweta@123");
            boolean isWelcomeMessageDisplayed = signInPage.isWelcomeMessageDisplayed();
            boolean isErrorMessageDisplayed = signInPage.isErrorMessageDisplayed();
            Thread.sleep(6000);
            if (myAccountPage.isAtMyAccountPage()) {
                String expectedMessage = "Welcome, Shweta More!";
                String actualMessage = myAccountPage.getWelcomeMessage();
                Assert.assertEquals(actualMessage, expectedMessage, "Login failed with valid credentials.");
            } else if (signInPage.isAtSignInPage()) {
                String expectedErrorMessage = "Invalid login or password.";
                String actualErrorMessage = signInPage.getErrorMessage();
                Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Login succeeded with invalid credentials.");
            } else {
                Assert.fail("Unexpected page after login attempt.");
            }
            takeScreenshot("ValidLogin");
        }

        @Test
        public void testInvalidLogin() throws InterruptedException, IOException {
            homePage.navigateToSignIn();
            signInPage.clearFields();
            signInPage.loginUser("invalid_email@example.com", "invalid_password");
            Thread.sleep(3000);

            String expectedMessage = "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.";
            String actualMessage = signInPage.getErrorMessage();  // Changed from SignInPage to signInPage (correct instance name)

            Assert.assertEquals(actualMessage, expectedMessage, "Login succeeded with invalid credentials.");
            takeScreenshot("invalidLogin");
        }

        @Test
        public void testLoginWithMissingFields() throws InterruptedException, IOException {
            homePage.navigateToSignIn();
            signInPage.clearFields();
            Thread.sleep(5000);
            signInPage.loginUser("", "valid_password");
            Thread.sleep(5000);

            String expectedMessage = "This is a required field.";
            String actualMessage = signInPage.getEmailErrorMessage(); // Changed from SignInPage to signInPage (correct instance name)

            Assert.assertEquals(actualMessage, expectedMessage, "Login succeeded with missing email.");

            signInPage.clearFields();  // Changed from SignInPage to signInPage (correct instance name)
            signInPage.loginUser("valid_email@example.com", "");

            actualMessage = signInPage.getPasswordErrorMessage();
            Assert.assertEquals(actualMessage, expectedMessage, "Login succeeded with missing password.");
            takeScreenshot("missingFields");
        }

        @AfterMethod
        public void tearDown() {
            driver.quit();
        }
    }