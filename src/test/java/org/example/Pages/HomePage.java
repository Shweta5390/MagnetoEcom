package org.example.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @FindBy(linkText = "Create an Account")
    WebElement createAccountLink;

    @FindBy(xpath = "//button[@class='action switch']")
    WebElement arrowLink;

    @FindBy(linkText = "My Account")
    WebElement MyAccountLink;

    @FindBy(linkText = "Sign In")
    WebElement SignInLink;

    @FindBy(id = "search")
    WebElement SearchBar;

    @FindBy(css = "button[type='submit']")
    WebElement SearchButton;

    @FindBy(linkText = "productName")
    WebElement Product;

    @FindBy(linkText = "categoryName")
    WebElement Category;

    @FindBy(xpath = "//a[text()='Men']")
    WebElement categoryMenuMen;

    @FindBy(xpath = "//a[@title='Radiant Tee']")
    WebElement RadiantTeeProduct;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
    }

    public void homePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);

    }

    public void navigateToSignIn() {
        SignInLink.click();
    }

    public void navigateToCreateAccount() {
        WebDriverWait wait = new WebDriverWait(driver, (100));
        createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Create an Account")));
        createAccountLink.click();
    }

    public void searchAndSelectProduct(String productName) {
        SearchBar.sendKeys(productName);
        SearchButton.click();
        Product.click();
    }

    public void navigateToCategory(String categoryName) {
        Category.click();
    }

    public void selectProductFromCategory(String productName) {
        driver.findElement(By.linkText(productName)).click();
    }

    public void navigateToSignInPage() {
        SignInLink.click();

    }

    public void navigateToMyAccountPage() {
        arrowLink.click();
        MyAccountLink.click();
    }

    public void clickOnCategoryMenuMen() {
        categoryMenuMen.click();
    }

    public void selectRadiantTeeProduct() {
        RadiantTeeProduct.click();
    }

    public void selectProduct(String productName) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            // Attempt to find and click the product link by its visible text
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(productName)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productLink);
            productLink.click();

        } catch (TimeoutException e) {
            System.out.println("Product link not found or not clickable: " + productName);

        } catch (StaleElementReferenceException e) {
            System.out.println("Stale element reference, retrying: " + productName);
            // Retry finding and clicking the product link if the element becomes stale
            retryClick(productName, wait);

        } catch (ElementClickInterceptedException e) {
            System.out.println("Element click intercepted, retrying: " + productName);
            // Scroll again to ensure the element is visible and not obstructed, then retry click
            retryClick(productName, wait);
        }
    }

    // Helper method to retry the click in case of exceptions
    private void retryClick(String productName, WebDriverWait wait) {
        try {
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(productName)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productLink);
            productLink.click();
        } catch (Exception e) {
            System.out.println("Failed to click product link after retry: " + productName);
        }
    }
}



