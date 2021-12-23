package homeWork.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;

public abstract class BaseTest {
    static Properties properties = new Properties();
    static String token;
    static String username;

    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpecOk;


    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
        getProperties();
        RestAssured.baseURI = properties.getProperty("baseUri");
        token = properties.getProperty("token");
        username = properties.getProperty("username");

        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.MULTIPART)
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.PARAMS)
                .build();

        responseSpecOk = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();

        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpecOk;

    }

    private static void getProperties() {
        try (InputStream output = new FileInputStream("src/test/resources/application.properties")) {
            properties.load(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
