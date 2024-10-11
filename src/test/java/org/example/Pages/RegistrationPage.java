package org.example.Pages;

import org.example.Execution.UserData;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
        WebDriver driver;
        WebDriverWait wait;

    @FindBy(id = "firstname")
    WebElement firstNameField;

    @FindBy(id = "lastname")
    WebElement lastNameField;

    @FindBy(id = "email_address")
    WebElement emailField;

    @FindBy(id = "password")
    WebElement passwordField;

    @FindBy(id = "password-confirmation")
    WebElement confirmPasswordField;

    @FindBy(xpath = "//button[@class='action submit primary']")
    WebElement createAnAccountButton;

    @FindBy(xpath = "//div[contains(@class, 'message-success')]")
    WebElement successMessage;

    @FindBy(css = "div.message-error")
    WebElement errorMessage;


    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return document.readyState").equals("complete");

    }

    public void typeIntoFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
    }

    public void typeIntoLastName(String lastName) {
        lastNameField.sendKeys(lastName);
    }

    public void typeIntoEmail(String email) {
        emailField.sendKeys(email);
    }

    public void typeIntoPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void typeIntoConfirmPassword(String confirm_password) {
        confirmPasswordField.sendKeys(confirm_password);
    }

    public void clickOnCreateAnAccountButton() throws InterruptedException {
        createAnAccountButton.click();
        Thread.sleep(3000);
    }
    public String getSuccessMessage() {
        return successMessage.isDisplayed() ? successMessage.getText() : "";
    }

    public String getErrorMessage() {
        return errorMessage.isDisplayed() ? errorMessage.getText() : "";
    }
    public String getFirstNameErrorMessage() {
        return driver.findElement(By.id("firstname-error")).getText(); // Replace with actual locator
    }

    public String getLastNameErrorMessage() throws InterruptedException {
        return driver.findElement(By.id("lastname-error")).getText(); // Replace with actual locator
    }

    public String getEmailErrorMessage() {
        return driver.findElement(By.id("email-error")).getText(); // Replace with actual locator
    }

    public String getPasswordErrorMessage() {
        return driver.findElement(By.id("password-error")).getText(); // Replace with actual locator
    }

    public String getConfirmPasswordErrorMessage() {
        return driver.findElement(By.id("confirmpassword-error")).getText(); // Replace with actual locator
    }

    public String getInvalidEmailErrorMessage() {
        return driver.findElement(By.id("email-error")).getText();
    }
}