package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ContactsPage
{
    WebDriver driver;
    WebDriverWait wait;

    //locators
    By firstNameLocator = By.id("first_name");
    By lastNameLocator = By.id("last_name");
    By emailLocator = By.id("email");
    By selectSubjectMenu = By.id("subject");
    By messageTextBoxLocator = By.id("message");
    By contactSubmitButton = By.xpath("//input[@data-test='contact-submit']");

    public ContactsPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public void fillInContactForm(String subjectName, String message)
    {
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameLocator));
        driver.findElement(firstNameLocator).sendKeys("John");
        driver.findElement(lastNameLocator).sendKeys("Doe");
        driver.findElement(emailLocator).sendKeys("john@doe.com");
        Select select = new Select(driver.findElement(selectSubjectMenu));
        List<String> subjectList = Arrays.asList("Webmaster", "Return", "Payments", "Warranty", "Status of my order");
        select.selectByVisibleText(subjectName);
        driver.findElement(messageTextBoxLocator).sendKeys(message);
        driver.findElement(contactSubmitButton).click();
    }
}
