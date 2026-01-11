package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsPage
{
    WebDriver driver;
    WebDriverWait wait;

    public ProductsPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public String searchForProduct(String searchQuery)
    {
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        By searchQueryInput = By.id("search-query");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchQueryInput));
        driver.findElement(searchQueryInput).sendKeys(searchQuery);
        driver.findElement(By.xpath("//button[@data-test='search-submit']")).click();
        By searchedTextLocator = By.xpath("//h3[@data-test='search-caption']//span");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchedTextLocator));
        return driver.findElement(searchedTextLocator).getText();
    }
}
