package api.smoke;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class APIHealthTest
{
    String baseURI = "https://api.practicesoftwaretesting.com";
    RequestSpecification requestSpec;

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

    @Test
    public void testProductsAPI()
    {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("products")
                .then()
                .statusCode(200)
                .extract()
                .response();
        System.out.println(response.asPrettyString());
    }
}
