package hybrid;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static utils.GenerateData.getUserRegistrationPayload;

public class CartSyncValidationTests
{
    /*
API: Create user and login (1s)
API: Add 3 products to cart (1s)
UI: Refresh page (2s)
UI: Verify cart badge shows "3" (1s)
UI: Open cart, verify all products present (2s)
Total: 7s vs 18s pure UI
     */

    String baseURI = "https://api.practicesoftwaretesting.com";
    RequestSpecification requestSpec;
    JsonPath jp;
    Faker faker;
    WebDriver driver;
    String token;

    @BeforeClass
    public void setupAPI()
    {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath("/")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
        faker = new Faker();
    }

    @Test
    public void validateCartSyncTest()  {
        String email = faker.internet().emailAddress();
        String password = "helloWorld01@";
        String requestBody = """
                {
                  "first_name": "John",
                  "last_name": "Doe",
                  "dob": "1992-05-15",
                  "phone": "12025550174",
                  "email": "generate_email",
                  "password": "generate_password",
                  "address": {
                    "street": "123 Automation Lane",
                    "city": "New York",
                    "state": "New York",
                    "country": "US",
                    "postal_code": "10001"
                  }
                }""".replace("generate_email", email).replace("generate_password", password);
               Response response = given().spec(requestSpec)
                .body(requestBody)
                .when().post("users/register")
                .then()
                .statusCode(201)
                .log().all().extract().response();
        System.out.println(email+" "+password);
        System.out.println(response.asPrettyString());

        //login

        String loginRequestBody = """
                {
                  "email": "generate_email",
                  "password": "generate_password"
                }""".replace("generate_email", email).replace("generate_password", password);
        Response loginResponse = given().spec(requestSpec)
                .body(loginRequestBody)
                .when().post("users/login")
                .then().statusCode(200)
                .log().all()
                .extract().response();

        jp = loginResponse.jsonPath();
         token = jp.getString("access_token");
        System.out.println("Token: "+token);

        // add item to cart using API
//        String addToCartRequestBody = """
//                {
//                    "product_id": "01KEBTYP6QXGVYY2A3H6DN02BJ",
//                    "quantity": 1
//                }
//                """;
//        String tokenAuth = "Bearer "+token;
//        System.out.println("TOKEN: "+tokenAuth);
//        given().spec(requestSpec)
//                .body(addToCartRequestBody)
//                .header("Authorization",tokenAuth)
//                .when().post("carts/01kebv0sa5kcfxyh8dv41g0kff")
//                .then().log().all();

        //injecting token in browser session now
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://practicesoftwaretesting.com/checkout");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem('auth-token', '"+token+"');");
        js.executeScript("window.sessionStorage.setItem('cart_id', '01keeyz28bed0me1j77r85hmyp');");
        js.executeScript("window.sessionStorage.setItem('cart_quantity', '1');");
        driver.navigate().refresh();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // add item to cart using API
        String addToCartRequestBody = """
                {
                    "product_id": "01keeyz28bed0me1j77r85hmyp",
                    "quantity": 1
                }
                """;
        String tokenAuth = "Bearer "+token;
        System.out.println("TOKEN: "+tokenAuth);
        given().spec(requestSpec)
                .body(addToCartRequestBody)
                .header("Authorization",tokenAuth)
                .when().post("carts/01keeyz28bed0me1j77r85hmyp")
                .then().log().all();
        driver.navigate().refresh();
        ////span[@data-test='cart-quantity']

        ///validate icon and quantity
    }

    @Test
    public void addToCart()
    {
                String addToCartRequestBody = """
                {
                    "product_id": "01KEBTYP6QXGVYY2A3H6DN02BJ",
                    "quantity": 1
                }
                """;
        String tokenAuth = "Bearer "+token;
        System.out.println("TOKEN: "+tokenAuth);
        given().spec(requestSpec)
                .body(addToCartRequestBody)
                .header("Authorization",tokenAuth)
                .when().post("carts/01kebv0sa5kcfxyh8dv41g0kff")
                        .then().log().all();
    }

    @AfterClass
    public void tearDown()
    {
        driver.quit();
    }

}
