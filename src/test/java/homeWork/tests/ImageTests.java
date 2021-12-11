package homeWork.tests;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;


public class ImageTests extends BaseTest {
    private final String PATH_TO_IMAGE = "src/test/resources/york.jpg";
    static String encodedFile; //переменная под закодированный файл
    String uploadedImageId; //переменная id загруженной картинки
    static String uploadedImageIdForUpdate = null; //переменная id для изменения информации о картинке

    String fileTitle = "My dog";
    String fileType = "jpeg";
    String titleForUpdate = "Heart";
    String descriptionForUpdate = "This is an image of a heart outline.";

    @Nested
    class ImageUploadTests {
        @BeforeEach
        void beforeTest() {
            byte[] byteArray = getFileContent();
            encodedFile = Base64.getEncoder().encodeToString(byteArray);
        }

        @AfterEach
        void tearDown() {
            given()
                    .header("Authorization", token)
                    .when()
                    .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", "testprogmath", uploadedImageId)
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        @Test
        void uploadFileImage() {
            uploadedImageId = given()
                    .headers("Authorization", token)
                    .multiPart("image", new File(PATH_TO_IMAGE))
                    .log()
                    .method()
                    .log()
                    .uri()
                    .expect()
                    .contentType("application/json")
                    .statusCode(200)
                    .body("data.type", equalTo("image/" + fileType))
                    .body("data.title", is(nullValue()))
                    .body("data.id", is(notNullValue()))
                    .body("success", equalTo(true))
                    .body("status", equalTo(200))
                    .when()
                    .post("https://api.imgur.com/3/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @Test
        void uploadFileImageWithTypeAndTitleTest() {
            uploadedImageId = given()
                    .headers("Authorization", token)
                    .multiPart("image", new File(PATH_TO_IMAGE))
                    .multiPart("type", fileType)
                    .multiPart("title", fileTitle)
                    .log()
                    .method()
                    .log()
                    .uri()
                    .expect()
                    .contentType("application/json")
                    .statusCode(200)
                    .body("data.type", equalTo("image/" + fileType))
                    .body("data.title", equalTo(fileTitle))
                    .body("data.id", is(notNullValue()))
                    .body("success", equalTo(true))
                    .body("status", equalTo(200))
                    .when()
                    .post("https://api.imgur.com/3/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @Test
        void uploadFileImageWithTypeTest() {
            uploadedImageId = given()
                    .headers("Authorization", token)
                    .multiPart("image", new File(PATH_TO_IMAGE))
                    .multiPart("type", fileType)
                    .log()
                    .method()
                    .log()
                    .uri()
                    .expect()
                    .contentType("application/json")
                    .statusCode(200)
                    .body("data.type", equalTo("image/" + fileType))
                    .body("data.title", is(nullValue()))
                    .body("data.id", is(notNullValue()))
                    .body("success", equalTo(true))
                    .body("status", equalTo(200))
                    .when()
                    .post("https://api.imgur.com/3/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @Test
        void uploadFileImageWithTitleTest() {
            uploadedImageId = given()
                    .headers("Authorization", token)
                    .multiPart("image", new File(PATH_TO_IMAGE))
                    .multiPart("title", fileTitle)
                    .log()
                    .method()
                    .log()
                    .uri()
                    .expect()
                    .contentType("application/json")
                    .statusCode(200)
                    .body("data.type", equalTo("image/" + fileType))
                    .body("data.title", equalTo(fileTitle))
                    .body("data.id", is(notNullValue()))
                    .body("success", equalTo(true))
                    .body("status", equalTo(200))
                    .when()
                    .post("https://api.imgur.com/3/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @Test
        void uploadFileImageBase64() {
            uploadedImageId = given()
                    .headers("Authorization", token)
                    .multiPart("image", encodedFile)
                    .log()
                    .method()
                    .log()
                    .uri()
                    .expect()
                    .contentType("application/json")
                    .statusCode(200)
                    .body("data.type", equalTo("image/" + fileType))
                    .body("data.title", is(nullValue()))
                    .body("data.id", is(notNullValue()))
                    .body("success", equalTo(true))
                    .body("status", equalTo(200))
                    .when()
                    .post("https://api.imgur.com/3/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @Test
        void uploadFileImageBase64WithTypeAndTitleTest() {
            uploadedImageId = given()
                    .headers("Authorization", token)
                    .multiPart("image", encodedFile)
                    .multiPart("type", fileType)
                    .multiPart("title", fileTitle)
                    .log()
                    .method()
                    .log()
                    .uri()
                    .expect()
                    .contentType("application/json")
                    .statusCode(200)
                    .body("data.type", equalTo("image/" + fileType))
                    .body("data.title", equalTo(fileTitle))
                    .body("data.id", is(notNullValue()))
                    .body("success", equalTo(true))
                    .body("status", equalTo(200))
                    .when()
                    .post("https://api.imgur.com/3/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @Test
        void uploadFileImageBase64WithTypeTest() {
            uploadedImageId = given()
                    .headers("Authorization", token)
                    .multiPart("image", encodedFile)
                    .multiPart("type", fileType)
                    .log()
                    .method()
                    .log()
                    .uri()
                    .expect()
                    .contentType("application/json")
                    .statusCode(200)
                    .body("data.type", equalTo("image/" + fileType))
                    .body("data.title", is(nullValue()))
                    .body("data.id", is(notNullValue()))
                    .body("success", equalTo(true))
                    .body("status", equalTo(200))
                    .when()
                    .post("https://api.imgur.com/3/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @Test
        void uploadFileImageBase64WithTitleTest() {
            uploadedImageId = given()
                    .headers("Authorization", token)
                    .multiPart("image", encodedFile)
                    .multiPart("title", fileTitle)
                    .log()
                    .method()
                    .log()
                    .uri()
                    .expect()
                    .contentType("application/json")
                    .statusCode(200)
                    .body("data.type", equalTo("image/" + fileType))
                    .body("data.title", equalTo(fileTitle))
                    .body("data.id", is(notNullValue()))
                    .body("success", equalTo(true))
                    .body("status", equalTo(200))
                    .when()
                    .post("https://api.imgur.com/3/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }
    }

    @Test
    void uploadFileImage() {
        uploadedImageIdForUpdate = given()
                .headers("Authorization", token)
                .multiPart("image", new File(PATH_TO_IMAGE))
                .log()
                .method()
                .log()
                .uri()
                .expect()
                .contentType("application/json")
                .statusCode(200)
                .body("data.type", equalTo("image/" + fileType))
                .body("data.title", is(nullValue()))
                .body("data.id", is(notNullValue()))
                .body("success", equalTo(true))
                .body("status", equalTo(200))
                .when()
                .post("https://api.imgur.com/3/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");

        System.out.println(uploadedImageIdForUpdate);
    }

    @Test
    void updateImageInfo() {
        given()
                .headers("Authorization", token)
                .multiPart("title", titleForUpdate)
                .multiPart("description", descriptionForUpdate)
                .log()
                .all()
                .expect()
                .contentType("application/json")
                .statusCode(200)
                .body("data", equalTo(true))
                .body("success", equalTo(true))
                .body("status", equalTo(200))
                .when()
                .post("https://api.imgur.com/3/image/{uploadedImageIdForUpdate}", uploadedImageIdForUpdate)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    private byte[] getFileContent() {
        byte[] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(PATH_TO_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

}
