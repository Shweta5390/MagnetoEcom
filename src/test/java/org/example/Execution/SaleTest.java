package org.example.Execution;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.Pages.SalePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleTest {
        WebDriver driver;
        WebDriverWait wait;
        SalePage salePage;


    @BeforeClass
    public void launchBrowser() {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
        driver = new FirefoxDriver();

        driver.get("https://magento.softwaretestingboard.com/");
        driver.manage().window().maximize();

            salePage = new SalePage(driver);
        }

        @Test
        public void validateTeesAvailableUnderSaleForWomen() {
            // Navigate to the Sale section for Women
            salePage.navigateToSaleForWomen();

            // Validate if 'Tees' is available
            boolean isTeesPresent = salePage.isTeesAvailable();
            Assert.assertTrue(isTeesPresent, "Tees is not available under Sale for Women.");
            String Link= driver.getTitle();
            System.out.println(Link);
        }

        @AfterClass
        public void tearDown() {

            if (driver != null) {
                driver.quit();
            }
        }
    }


