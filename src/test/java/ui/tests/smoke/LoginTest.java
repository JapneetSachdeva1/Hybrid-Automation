package ui.tests.smoke;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;

import java.time.Duration;

public class LoginTest
{
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
    public void testValidUserLogin()
    {
        //Arrange
        loginPage = new LoginPage(driver);
        loginPage.loginUserWithUI("admin@practicesoftwaretesting.com", "welcome01");
        By homePageTitle = By.xpath("//h1[@data-test='page-title']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageTitle));
        //Act
        String homePageTitleText =  driver.findElement(homePageTitle).getText();
        //Assert
        Assert.assertEquals(homePageTitleText, "Sales over the years");
    }

    @AfterClass
    public void tearDownTests()
    {
        driver.quit();
    }
}
