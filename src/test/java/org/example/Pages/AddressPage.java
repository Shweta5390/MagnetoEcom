package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddressPage {

     WebDriver driver;
     WebDriverWait wait;

    // WebElements
    @FindBy(id = "company")
     WebElement companyField;

    @FindBy(id = "street_1")
     WebElement address1Field;

    @FindBy(id = "city")
     WebElement cityField;

    @FindBy(id="region_id")
    WebElement RegionField;

    @FindBy(id = "zip")
     WebElement postcodeField;

    @FindBy(id = "country")
     WebElement countryField;

    @FindBy(id = "country")
    WebElement countryDropdown;

    @FindBy(id = "region_id")
     WebElement regionDropdown;

    @FindBy(id = "telephone")
     WebElement telephoneField;

    @FindBy(xpath = "//button[@class='action save primary']")
     WebElement saveAddressButton;


    // Constructor
    public AddressPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    // Method to enter address details
    public void enterAddressDetails(String company, String address1, String city, String postcode,String country,String Region, String telephone) {
        companyField.sendKeys(company);
        address1Field.sendKeys(address1);
        cityField.sendKeys(city);
        postcodeField.sendKeys(postcode);
        RegionField.sendKeys(Region);
        countryField.sendKeys(country);
        telephoneField.sendKeys(telephone);

    }

    public void selectCountry(String country) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement countryElement = wait.until(ExpectedConditions.elementToBeClickable(countryDropdown));
        Select countrySelect = new Select(countryElement);
        countrySelect.selectByVisibleText(country);
    }
    public void selectRegion(String Region) {
        WebDriverWait wait = new WebDriverWait(driver, 25);
        WebElement RegionElement = wait.until(ExpectedConditions.elementToBeClickable(regionDropdown));
        Select RegionSelect = new Select(RegionElement);
        RegionSelect.selectByVisibleText(Region);
    }


    public void clickOnSaveAddressButton() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement clickableButton = wait.until(ExpectedConditions.elementToBeClickable(saveAddressButton));
        clickableButton.click();
    }

    public boolean isAddressSavedSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'message-success')]")));
        return successMessage.isDisplayed();
    }
}
