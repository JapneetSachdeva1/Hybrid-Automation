package ui.tests.regression;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ContactTest
{
    /*
    Login with invalid credentials - error message
     */

    WebDriver driver;
    WebDriverWait wait;
    ContactsPage contactsPage;

    @BeforeClass
    public void setupTests()
    {
        //Arrange
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://practicesoftwaretesting.com/contact");
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    //@Test(dataProvider = "userContact")
    @Test
    public void testUserContact()
    {
        Faker faker = new Faker();
        contactsPage = new ContactsPage(driver);

        List<String> subjectList = Arrays.asList("Webmaster", "Return", "Payments", "Warranty", "Status of my order");
        String randomSubject = faker.options().nextElement(subjectList);
        contactsPage.fillInContactForm(randomSubject,faker.lorem().characters(52));
        System.out.println(randomSubject);
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
