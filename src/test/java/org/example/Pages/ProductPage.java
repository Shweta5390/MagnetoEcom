package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Instant;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//div[@class='swatch-attribute size']")
    WebElement sizeField;
    @FindBy(xpath = "//div[@aria-label='S']")
    WebElement sizeS;
    @FindBy(xpath = "//div[@aria-label='M']")
    WebElement sizeM;
    @FindBy(xpath = "//div[@aria-label='L']")
    WebElement sizeL;
    @FindBy(xpath = "//div[@aria-label='XL']")
    WebElement sizeXL;

    @FindBy(xpath = "//div[@class='swatch-attribute color']")
    WebElement colorOption;
    @FindBy(xpath = "//div[@aria-label='Blue']")
    WebElement colorBlue;
    @FindBy(xpath = "//div[@aria-label='Orange']")
    WebElement colorOrange;
    @FindBy(xpath = "//div[@aria-label='Purple']")
    WebElement colorPurple;

    @FindBy(xpath = "//span[text()='Add to Cart']")
    WebElement addToCartButton;
    @FindBy(xpath="//a[@class='action showcart']")
    WebElement showCart;
    @FindBy(xpath = "//button[@class='action-primary action-accept']")
    WebElement acceptButton;


    @FindBy(xpath = "//div[@class='message-success success message']")
    WebElement successMessage;

    public ProductPage(WebDriver driver,WebDriverWait wait) {
        this.driver = driver;
        this.wait=wait;
        PageFactory.initElements(driver, this);
    }


    public void selectSize(String size) {
        WebElement selectedSize = null;
        switch (size.toUpperCase()) {
            case "S":
                selectedSize = sizeS;
                break;
            case "M":
                selectedSize = sizeM;
                break;
            case "L":
                selectedSize = sizeL;
                break;
            case "XL":
                selectedSize = sizeXL;
                break;
            default:
                throw new IllegalArgumentException("Invalid size: " + size);
        }
        if (selectedSize != null) {
            selectedSize.click();
        }
    }

    // Method to select a color based on the input parameter
    public void selectColor(String color) {
        WebElement selectedColor = null;
        switch (color.toUpperCase()) {
            case "BLUE":
                selectedColor = colorBlue;
                break;
            case "ORANGE":
                selectedColor = colorOrange;
                break;
            case "PURPLE":
                selectedColor = colorPurple;
                break;
            default:
                throw new IllegalArgumentException("Invalid color: " + color);
        }
        if (selectedColor != null) {
            selectedColor.click();
        }
    }

    public void addToCart() throws InterruptedException {
       WebElement addToCartButton1 =wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton1.click();
    }

    public void clickOnShowCart(){
        showCart.click();
    }
    public void clickOnAcceptButton(){
        acceptButton.click();
    }

    public String getSuccessMessage() {
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message-success success message']")));
        String expectedMessage = "You added Radiant Tee to your shopping cart.";
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Added cart message did not match.");
        return expectedMessage;
    }


}