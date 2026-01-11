package ui.tests.regression;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class CheckoutFlowTest
{
    /*
        Complete purchase with cash on delivery
         */
    WebDriver driver;
    WebDriverWait wait;
    LoginPage loginPage;

    @BeforeClass
    public void setupTests()
    {
        //Arrange
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://practicesoftwaretesting.com/auth/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    @Test
    public void checkoutFlowUITest()
    {
        loginPage = new LoginPage(driver);
        String itemName = "Combination Pliers";
        loginPage.loginUserWithUI("admin@practicesoftwaretesting.com", "welcome01");
        wait.until(ExpectedConditions.urlContains("dashboard"));
        driver.findElement(By.xpath("//a[@data-test='nav-home']")).click();
        By byValue = By.xpath("//h5[contains(text(), '"+itemName+"')]//ancestor::a");
        wait.until(ExpectedConditions.elementToBeClickable(byValue));
        driver.findElement(byValue).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='"+itemName+"']")));
        driver.findElement(By.id("btn-add-to-cart")).click();
        By itemAddedToCartAlertMessage = By.xpath("//div[@role='alert']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(itemAddedToCartAlertMessage));
        String alertText = driver.findElement(itemAddedToCartAlertMessage).getText();
        Assert.assertEquals(alertText, "Product added to shopping cart.");
        driver.findElement(By.xpath("//a[@data-test='nav-cart']")).click();
        By productTitleText = By.xpath("//span[@data-test='product-title']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(productTitleText));
        Assert.assertEquals(driver.findElement(productTitleText).getText().trim(), itemName);
        driver.findElement(By.xpath("//button[@data-test='proceed-1']")).click();
        driver.findElement(By.xpath("//button[@data-test='proceed-2']")).click();
        for(int i = 0; i<2; i++) {
            try {
                driver.findElement(By.id("state")).sendKeys("New York");
                driver.findElement(By.id("postal_code")).sendKeys("3");
                driver.findElement(By.xpath("//button[@data-test='proceed-3']")).click();
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }
        }
        Select select = new Select(driver.findElement(By.id("payment-method")));
        select.selectByValue("cash-on-delivery");
        driver.findElement(By.xpath("//button[@data-test='finish']")).click();
        By successMessageLocator = By.xpath("//div[@data-test='payment-success-message']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
        String successMessageText = driver.findElement(successMessageLocator).getText();
        Assert.assertEquals(successMessageText, "Payment was successful");
    }

    @AfterClass
    public void tearDownTests()
    {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        driver.quit();
    }

}
