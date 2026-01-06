package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage
{
    private WebDriver driver;

    public LoginPage(WebDriver webDriver)
    {
        this.driver = webDriver;
    }

    public void loginUserWithUI(String email, String password)
    {
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@data-test='login-submit']")).click();
    }


}
