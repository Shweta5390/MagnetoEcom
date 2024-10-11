package org.example.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "billing:firstname")
    WebElement firstNameField;

    @FindBy(id = "billing:lastname")
    WebElement lastNameField;

    @FindBy(id = "billing:street1")
    WebElement streetAddressField;

    @FindBy(id = "billing:city")
    WebElement cityField;

    @FindBy(id = "billing:postcode")
    WebElement postalCodeField;

    @FindBy(id = "billing:country_id")
    WebElement countryDropdown;

    @FindBy(id = "billing:region_id")
    WebElement stateDropdown;

    @FindBy(id = "billing:telephone")
    WebElement telephoneField;

    @FindBy(xpath = "//button[@title='Place Order']")
    WebElement placeOrderButton;

    @FindBy(xpath = "//div[@class='checkout-success']//p[contains(text(),'Order #')]")
    WebElement orderNumberText;

    public CheckoutPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait= wait;
        PageFactory.initElements(driver, this);
    }

    public void fillBillingDetails(String firstName, String lastName, String streetAddress, String city,
                                   String postalCode, String country, String state, String telephone) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        streetAddressField.sendKeys(streetAddress);
        cityField.sendKeys(city);
        postalCodeField.sendKeys(postalCode);
        // Select country and state using dropdown handling methods
        // Example: selectByVisibleText(countryDropdown, country);
        // Example: selectByVisibleText(stateDropdown, state);
        telephoneField.sendKeys(telephone);
    }

    public void placeOrder() {
        WebElement placeOrderBtn = wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));
        placeOrderBtn.click();
    }

    public String getOrderNumber() {
        return orderNumberText.getText().split("#")[1].trim();
    }
}
