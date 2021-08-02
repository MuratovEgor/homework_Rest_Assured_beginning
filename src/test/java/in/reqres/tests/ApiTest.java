package in.reqres.tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {

    int userID = 3;
    String baseURL = "https://reqres.in",
            firstName = "firstName",
            job = "job",
            email = "eve.holt@reqres.in",
            password = "cityslicka",
            token = "QpwL5tke4Pnpja7X4";

    @Test
    public void postRequestSuccessfulLogin() {
        given()
                .contentType(JSON)
                .body("{\"email\": " + "\"" + email + "\"" + "," +
                        "\"password\": " + "\"" + password + "\"}")
                .when()
                .post(baseURL + "/api/login")
                .then()
                .statusCode(200)
                .body("token", is(token));
    }

    @Test
    public void postRequestUnsuccessfulLogin() {
        given()
                .contentType(JSON)
                .body("{\"email\": " + "\"" + email + "\"}")
                .when()
                .post(baseURL + "/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    public void getRequestUserById() {
        get(baseURL + "/api/users/" + userID).then()
                .statusCode(200).body("data.id", is(3));
    }

    @Test
    public void getRequestUserNotFound() {
        String res = get(baseURL + "/api/users/404").then()
                .statusCode(404).extract().response().asString();

        assertEquals("{}", res);
    }

    @Test
    public void createUserRequest() {
        given()
                .contentType(JSON)
                .body("{\"name\": " + "\"" + firstName + "\"" + "," +
                        "\"job\": " + "\"" + job + "\"}")
                .when()
                .post(baseURL + "/api/users")
                .then()
                .statusCode(201)
                .body("name", is(firstName))
                .body("job", is(job));
    }

    @Test
    public void deleteUserRequest() {
        delete(baseURL + "/api/users/" + userID).then()
                .statusCode(204);
    }
}
