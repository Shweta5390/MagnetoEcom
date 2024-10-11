package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//button[@class='action switch']")
    WebElement arrowLink;

    @FindBy(xpath="//strong[text()='Address Book']")
    WebElement addressBook;

    @FindBy(id = "Sign Out ")
    WebElement SignOut;

    @FindBy(xpath = "//div[contains(@class, 'message-success')]")
    //div[contains(text(), 'Thank you for registering with Main Website Store.')]
    WebElement successMessage;

    @FindBy(xpath="//span[@class='base']")
    WebElement MyAccountText;

    @FindBy(xpath = "(//span[contains(@class, 'logged-in') and contains(text(), 'Welcome')])[1]")
    WebElement welcomeMessage;

    @FindBy(xpath="//a[text()='My Account']")
    WebElement myAccount;

    public MyAccountPage(WebDriver driver){
        this.driver=driver;
        this.wait=wait;
        PageFactory.initElements(driver, this);
    }

    public int getElementMyAccount(){
        return driver.findElements(By.xpath("//span[@class='base']")).size();
    }
    public void getSuccessMessage(){
       successMessage.getText();
   }
    public boolean isAtMyAccountPage() throws InterruptedException {
        Thread.sleep(5000);
        return welcomeMessage.isDisplayed();
    }

    public String getWelcomeMessage() {
        return welcomeMessage.getText();
    }


    public String MyAccount(){
      String Expected = MyAccountText.getText();
       return Expected;
   }
   public void clickOnSignOut(){
       SignOut.click();
   }

    public void clickOnAddressBook() {
        addressBook.click();
    }

    public void navigateToAddressBook() {
        addressBook.click();
    }
    public void clickOnArrowLink(){
        arrowLink.click();
    }
    public void goToMyAccount(){
        myAccount.click();
    }

    public void logout() {
        SignOut.click();
    }
}

