package org.example.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SignInPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//button[@class='action switch']")
    WebElement arrowLink;

    @FindBy(linkText = "")
    WebElement myAccountText;

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "pass")
    WebElement passwordField;

    @FindBy(id = "Sign In")
    WebElement SignInButton;

    @FindBy(xpath = "//button[contains(@class,'primary')]/span")
    WebElement SignInButton1;

    @FindBy(xpath = "//div[@class='message-error error message']")
    WebElement errorMessage;

    @FindBy(xpath = "//div[contains(@class, 'welcome-msg')]//span[contains(text(), 'Welcome')]")
    WebElement welcomeMessage;

    @FindBy(linkText = "createAccountLink")
    WebElement createAccountLink;

    @FindBy(id="email-error")
    WebElement EmailErrorMessage;

    @FindBy(id="pass-error")
    WebElement PasswordErrorMessage;

    public SignInPage(WebDriver driver) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void loginUser(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        SignInButton1.click();
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickOnSignInButton() {
        SignInButton.click();
    }
    public boolean isAtSignInPage() {
        return SignInButton.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
    public String getEmailErrorMessage() throws InterruptedException {
        Thread.sleep(5000);
       return EmailErrorMessage.getText();
    }
    public String getPasswordErrorMessage(){
        return PasswordErrorMessage.getText();
    }
    public boolean isRegistrationSuccessful() {
        // Implement logic to check if registration was successful
        return true; // Replace with actual implementation
    }

    public boolean isWelcomeMessageDisplayed() {
        try {
            return welcomeMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickCreateAccountLink() {
        createAccountLink.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickOnSignInButton();
    }
    public void signIn(String email, String password) throws InterruptedException {
        (emailField).sendKeys(email);
        (passwordField).sendKeys(password);
        clickOnSignInButton1();
    }

    private void clickOnSignInButton1() {
        SignInButton1.click();
    }

    public void clearFields () {
        emailField.clear();
        passwordField.clear();
    }
    public void navigateToMyAccount(){
        arrowLink.click();
        myAccountText.click();
    }
    }



