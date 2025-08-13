package ru.academits;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Reqres {

    @Test
    public void createUserWithParamInMap() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "ivan");
        requestBody.put("job", "lawyer");

        Response response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/users")
                .andReturn();
        response.prettyPrint();

        Assertions.assertEquals(201, response.getStatusCode());
        Assertions.assertEquals(requestBody.get("name"), response.jsonPath().getString("name"));
    }


    @Test
    public void createUserWithParam() {
        Response response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body("{\n" +
                            "\t\"name\": \"ivan\",\n" +
                            "\t\"job\": \"lawyer\"\n" +
                            "}")
                .when()
                .post("https://reqres.in/api/users")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println("Status code: " + statusCode);

        assertEquals(201, statusCode, "Unexpected status code: expected 201, but was + statusCode");

        System.out.println("Content-Type: " + response.header("Content-Type"));
        System.out.println("Response: \n" + response.asPrettyString());

        String[] expectedKeys = {"name", "job", "id", "createdAt"};

        for (String expectedKey: expectedKeys) {
            response.then().assertThat().body("$", hasKey(expectedKey));
        }
    }


    @Test
    public void testParseJson() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "ivan");
        requestBody.put("job", "lawyer");

        JsonPath response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/users")
                .jsonPath();

        Assertions.assertEquals(requestBody.get("name"), response.get("name").toString());
        Assertions.assertEquals(requestBody.get("job"), response.get("job").toString());
    }


    @Test // негативный
    public void createUserWithoutBody() {
        Map<String, String> requestBody = new HashMap<>();

        ValidatableResponse response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201);
    }


    @Test
    public void testDate() {
        Response response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body("{\n" +
                        "\t\"name\": \"ivan\",\n" +
                        "\t\"job\": \"lawyer\"\n" +
                        "}")
                .when()
                .post("https://reqres.in/api/users")
                .andReturn();
        response.prettyPrint();

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(formatter);

        assertEquals(201, response.getStatusCode());
        Assertions.assertTrue(response.jsonPath().getString("createdAt").contains(formattedDate));
    }
}