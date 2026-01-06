package api.smoke;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class APIHealthTest
{
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
                .time(lessThan(2000L))
                .body("data.id", everyItem(notNullValue()))
                .body("data.price", everyItem(greaterThan(0.0f)))
                .extract()
                .response();
        jp = response.jsonPath();
        //System.out.println(response.asPrettyString());
        Assert.assertEquals(jp.get("data[0].id"), "01KE9P9KJ1KR290CZQBXG0MT7P");
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
