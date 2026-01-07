package ui.tests.regression;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.time.Duration;

public class ProductDiscoveryTest
{
    /*
    Search with special characters (!@#$%)
     */

    //atomic test -> component test -> testing specific scenario
    WebDriver driver;
    ProductsPage productsPage;

    @BeforeClass
    public void setupTests()
    {
        //Arrange
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--start-fullscreen");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://practicesoftwaretesting.com/");
    }

    @Test
    public void searchForProductWithSpecialCharacters()
    {
        productsPage = new ProductsPage(driver);
        String textToBeSearched = "!@#$%";
        String searchedText = productsPage.searchForProduct(textToBeSearched);
        Assert.assertEquals(searchedText, textToBeSearched);
    }

    @AfterClass
    public void tearDownTests()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }

}
