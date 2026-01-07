package api.regression;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static utils.GenerateData.getUserRegistrationPayload;

public class UserRegistrationAPITest
{
    /*
User registration works

API: POST /users/register → 201
UI: Success message shown
New user can login immediately
Use Faker with Data Provider
     */

    String baseURI = "https://api.practicesoftwaretesting.com";
    RequestSpecification requestSpec;
    JsonPath jp;

    @BeforeClass
    public void setupAPI()
    {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath("/")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test(dataProvider = "registrationTests")
    public void testNewUserRegistration(String scenario, String password, int statusCode) throws IOException {
        System.out.println("Executing "+scenario+" scenario");
        Response response = given().spec(requestSpec)
                .body(getUserRegistrationPayload(password))
                .log().all()
                .when().post("users/register")
                .then()
                .statusCode(statusCode)
                .log().all()
                .extract().response();
        System.out.println(response.asPrettyString());
    }

    @DataProvider(name = "registrationTests")
    public Object[][] getData()
    {
        return new Object[][]{
                {"Valid", "helloWorld01@#", 201},
                {"InValid", "Welcom01@", 422}
        };
    }

}
