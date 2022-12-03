package com.rikza;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.vertx.http.runtime.devmode.Json;
import io.restassured.internal.common.assertion.Assertion;
// import io.restassured.response.Response;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.aayushatharva.brotli4j.decoder.DecoderJNI.Status;
import com.google.inject.Inject;
import com.rikza.User.User;
import com.rikza.User.UserResource;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import java.io.*;
import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceTest {

    @Inject
    UserResource userResource;

    @Test
    @Order(1)
    public void addUser() {
        User testUser = new User();
        // System.out.println("MAX USER ID:"+userResource.getMaxUserId());
        testUser.setMrms_user_id(3);
        testUser.setMrms_username("raeshaazriyan");
        testUser.setMrms_password("12345");

        JsonbConfig config = new JsonbConfig().withFormatting(true);
        Jsonb jsonb = JsonbBuilder.newBuilder().withConfig(config).build();

        String serialized = jsonb.toJson(testUser);
        System.out.println(serialized);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(serialized)
                .when()
                .post("/user")
                .then()
                .statusCode(200);

    }

    @Test
    @Order(2)
    public void updateUser() {
        User testUser = new User();
        testUser.setMrms_user_id(3);
        testUser.setMrms_username("raeshaazriyansatifa");
        testUser.setMrms_password("12345");

        JsonbConfig config = new JsonbConfig().withFormatting(true);
        Jsonb jsonb = JsonbBuilder.newBuilder().withConfig(config).build();

        String serialized = jsonb.toJson(testUser);

        given()
                .when()
                .get("/user/" + testUser.getMrms_user_id())
                .then()
                .body("size()", equalTo(1))
                .statusCode(200);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(serialized)
                .when()
                .put("/user")
                .then()
                .statusCode(200);

    }

    @Test
    @Order(3)
    public void deleteUser() {
        given()
                .when()
                .get("/user/" + 3)
                .then()
                .body("size()", equalTo(1))
                .statusCode(200);

        given()
                .when()
                .delete("/user/3")
                .then()
                .statusCode(200);

    }

}
