package org.example.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = ".product-item")
    WebElement cartItems;

    @FindBy(xpath = "//input[@name='ko_unique_1']")
    WebElement tableRateCheckbox;

    @FindBy(xpath = "//input[@name='ko_unique_2']")
    WebElement fixedRateCheckbox;

    @FindBy(id="top-cart-btn-checkout")
    WebElement proceedToCheckOutButton;

    @FindBy(xpath ="//button[@class='button action continue primary']")
    WebElement nextButton;

    @FindBy(xpath = "//button[@class='action primary checkout']")
    WebElement placeOrderButton;

    @FindBy(xpath = "//span[@class='counter-number']")
    WebElement cartItemCount;

    @FindBy(xpath = "//a[@title='Remove item']")
    WebElement cartItemRemoveButton;

    @FindBy(xpath="//span[@text()='Continue Shopping']")
    WebElement ContinueShoppingButton;

    // Locators
    By proceedToCheckoutButton = By.xpath("//button[@title='Proceed to Checkout']");
//    By cartItemCount = By.xpath("//span[@class='counter-number']"); // Adjust as needed
//    By cartItemRemoveButton = By.xpath("//a[@title='Remove item']"); // Adjust as needed
    By shippingMethodCheckboxes = By.cssSelector("input[type='radio'][name='shipping_method']"); // Locator for shipping method checkboxes
    By loader = By.cssSelector("img[src*='loader-2.gif']"); // Adjust if needed

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, (30)); // Increased wait time
        PageFactory.initElements(driver, this);
    }

    public void clickOnNextButton() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("checkout-loader")));

        // Scroll to the button (if needed)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextButton);

        // Click the button using JavaScript (if needed)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextButton);
    }

    public void clickOnPlaceOrderButton() {
        // Ensure that the loader and placeOrderButton locators are defined correctly
        By loader = By.cssSelector("img[src*='loader-2.gif']"); // Example selector; adjust as needed
        By placeOrderButton = By.xpath("//button[@class='action primary checkout']"); // Example XPath; adjust as needed
        By orderNumberLocator = By.xpath("//a[@class='order-number']");// Example XPath for order number; adjust as needed

        // Create a WebDriverWait instance if not already created
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            // Wait for the loader to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));

            // Wait for the placeOrderButton to be visible and clickable
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));

            // Scroll to the button (if needed)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

            // Click the button using JavaScript (if needed)
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

            // Wait for the order confirmation element to be visible (e.g., an element containing the order number)
            WebElement orderNumberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumberLocator));

            // Extract and print the order number
            String orderNumber = orderNumberElement.getText();
            System.out.println("Order Number: " + orderNumber);

        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, handle specific exceptions or take a screenshot
        }
    }



    public void deleteProduct(String productName) {
        List<WebElement> removeButtons = driver.findElements(By.xpath("//a[@title='Remove item']"));
        for (WebElement removeButton : removeButtons) {
            if (removeButton.isDisplayed()) {
                removeButton.click();
                break;
            }
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(text(),'" + productName + "')]")));
    }

    public void proceedToCheckout() {

        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckOutButton));
        checkoutBtn.click();
    }
    public void clickOnContinue(){
        ContinueShoppingButton.click();
    }



    // Method to get the number of items in the cart
    public int getNumberOfItemsInCart() {
        try {
            // Define the locator for the cart item count
            By cartItemCount = By.cssSelector(".cart-item-count"); // Adjust this selector based on your actual cart item count element

            // Create a WebDriverWait instance if not already created
            WebDriverWait wait = new WebDriverWait(driver, 10); // Adjust timeout as needed

            // Wait for the element displaying the item count to be visible
            WebElement itemCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemCount));

            // Get the text of the element
            String itemCountText = itemCountElement.getText().trim(); // Trim any extra whitespace

            // Parse the item count text to an integer
            // Handle cases where the text might not be a simple number
            return parseItemCount(itemCountText);

        } catch (TimeoutException e) {
            System.err.println("Timed out waiting for the cart item count element: " + e.getMessage());
            return 0; // Or throw an exception if preferred
        } catch (NumberFormatException e) {
            System.err.println("Error parsing the item count text: " + e.getMessage());
            return 0; // Or throw an exception if preferred
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Or throw an exception if preferred
        }
    }

    private int parseItemCount(String text) {
        // Remove any non-numeric characters from the text
        String numericText = text.replaceAll("[^0-9]", "");
        try {
            return Integer.parseInt(numericText);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric value from text: " + e.getMessage());
            return 0; // Or throw an exception if preferred
        }
    }

    public void selectShippingMethod(String method) {
        By shippingMethodLocator;
        WebElement checkbox;

        // Determine the locator based on the method parameter
        if ("tableRate".equalsIgnoreCase(method)) {
            shippingMethodLocator = By.xpath("//input[@name='ko_unique_1']");
        } else if ("fixedRate".equalsIgnoreCase(method)) {
            shippingMethodLocator = By.xpath("//input[@name='ko_unique_2']");
        } else {
            throw new IllegalArgumentException("Invalid shipping method: " + method);
        }

        // Wait for the loader to disappear (if applicable)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("checkout-loader")));

        // Wait for the checkbox to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(shippingMethodLocator));

        // Find the checkbox element
        checkbox = driver.findElement(shippingMethodLocator);

        // Scroll to the checkbox element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);

        // Optionally, wait again for any other elements to disappear
        wait.until(ExpectedConditions.elementToBeClickable(shippingMethodLocator));

        try {
            // Try clicking the checkbox normally
            checkbox.click();
        } catch (ElementClickInterceptedException e) {
            // If the normal click fails, use JavaScript to click the element
            System.out.println("Normal click failed, using JavaScript to click the element.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
    }
}

