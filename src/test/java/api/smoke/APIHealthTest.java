package api.smoke;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

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
        HashMap<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("page","1");
        queryParamsMap.put("is_rental", "false");
        Response response = given()
                .spec(requestSpec)
                .queryParams(queryParamsMap)
                .when()
                .get("products")
                .then()
                .statusCode(200)
                .extract()
                .response();
        System.out.println(response.asPrettyString());
    }

    @Test
    public void testCategorySelectionAPI()
    {
        HashMap<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("by_category_slug", "hand-tools");
       Response response = given()
                .spec(requestSpec)
               .queryParams(queryParamsMap)
                .when()
                .get("categories/tree")
                .then()
                .statusCode(200)
                .extract()
                .response();

       System.out.println(response.asPrettyString());
    }
}
