package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

    public class SalePage {
        WebDriver driver;
        WebDriverWait wait;

        public SalePage(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, 30);
        }

        // Method to navigate to the Sale section for Women
        public void navigateToSaleForWomen() {
            WebElement womenSaleMenu = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Women")));
            womenSaleMenu.click();

            WebElement saleSubMenu = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sale")));
            saleSubMenu.click();
        }

        // Method to validate if 'Tees' is available under 'Sale' for Women
        public boolean isTeesAvailable() {
            try {
                WebElement teesCategory = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Tees")));
                return teesCategory.isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }
    }


