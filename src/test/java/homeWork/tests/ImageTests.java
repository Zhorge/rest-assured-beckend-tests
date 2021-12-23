package homeWork.tests;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

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

    static ResponseSpecification responseSpecBody;
    static ResponseSpecification responseSpecBodyFileType;
    static ResponseSpecification responseSpecBodyFileTitle;
    static ResponseSpecification responseSpecBodyFileTitleIsNull;
    static ResponseSpecification responseSpecBodyId;

    public static MultiPartSpecification multiPartSpecFile;
    public static MultiPartSpecification multiPartSpecFileBase64;
    public static MultiPartSpecification multiPartSpecFileType;
    public static MultiPartSpecification multiPartSpecFileTitle;

    @BeforeEach
    void beforeEach() {
        byte[] byteArray = getFileContent();
        encodedFile = Base64.getEncoder().encodeToString(byteArray);

        multiPartSpecFile = new MultiPartSpecBuilder(new File(PATH_TO_IMAGE))
                .controlName("image")
                .build();

        multiPartSpecFileBase64 = new MultiPartSpecBuilder(encodedFile)
                .controlName("image")
                .build();

        multiPartSpecFileType = new MultiPartSpecBuilder(fileType)
                .controlName("type")
                .build();

        multiPartSpecFileTitle = new MultiPartSpecBuilder(fileTitle)
                .controlName("title")
                .build();

        responseSpecBody = new ResponseSpecBuilder()
                .expectBody("success", equalTo(true))
                .expectBody("status", equalTo(200))
                .build();

        responseSpecBodyFileType = new ResponseSpecBuilder()
                .expectBody("data.type", equalTo("image/" + fileType))
                .build();

        responseSpecBodyFileTitle = new ResponseSpecBuilder()
                .expectBody("data.title", equalTo(fileTitle))
                .build();

        responseSpecBodyFileTitleIsNull = new ResponseSpecBuilder()
                .expectBody("data.title", is(nullValue()))
                .build();

        responseSpecBodyId = new ResponseSpecBuilder()
                .expectBody("data.id", is(notNullValue()))
                .build();
    }

    @Nested
    class ImageUploadTests {

        @AfterEach
        void tearDown() {
            given()
                    .when()
                    .delete("/account/{username}/image/{deleteHash}", "testprogmath", uploadedImageId)
                    .prettyPeek();
        }

        @DisplayName("Загрузка image")
        @Test
        void uploadFileImage() {
            uploadedImageId = given()
                    .multiPart(multiPartSpecFile)
                    .expect()
                    .spec(responseSpecBodyFileTitleIsNull)
                    .spec(responseSpecBodyId)
                    .spec(responseSpecBody)
                    .spec(responseSpecBodyFileType)
                    .when()
                    .post("/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @DisplayName("Загрузка image с параметрами Type и Title")
        @Test
        void uploadFileImageWithTypeAndTitleTest() {
            uploadedImageId = given()
                    .multiPart(multiPartSpecFile)
                    .multiPart(multiPartSpecFileType)
                    .multiPart(multiPartSpecFileTitle)
                    .expect()
                    .spec(responseSpecBodyFileType)
                    .spec(responseSpecBodyFileTitle)
                    .spec(responseSpecBodyId)
                    .spec(responseSpecBody)
                    .when()
                    .post("/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @DisplayName("Загрузка image с параметром Type")
        @Test
        void uploadFileImageWithTypeTest() {
            uploadedImageId = given()
                    .multiPart(multiPartSpecFile)
                    .multiPart(multiPartSpecFileType)
                    .expect()
                    .spec(responseSpecBodyFileType)
                    .spec(responseSpecBodyFileTitleIsNull)
                    .spec(responseSpecBodyId)
                    .spec(responseSpecBody)
                    .when()
                    .post("/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @DisplayName("Загрузка image с параметром Title")
        @Test
        void uploadFileImageWithTitleTest() {
            uploadedImageId = given()
                    .multiPart(multiPartSpecFile)
                    .multiPart(multiPartSpecFileTitle)
                    .expect()
                    .spec(responseSpecBodyFileType)
                    .spec(responseSpecBodyFileTitle)
                    .spec(responseSpecBodyId)
                    .spec(responseSpecBody)
                    .when()
                    .post("/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }


        @DisplayName("Загрузка imageBase64")
        @Test
        void uploadFileImageBase64() {
            uploadedImageId = given()
                    .multiPart(multiPartSpecFileBase64)
                    .expect()
                    .spec(responseSpecBodyFileType)
                    .spec(responseSpecBodyFileTitleIsNull)
                    .spec(responseSpecBodyId)
                    .spec(responseSpecBody)
                    .when()
                    .post("/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }


        @DisplayName("Загрузка imageBase64 с параметрами Type и Title")
        @Test
        void uploadFileImageBase64WithTypeAndTitleTest() {
            uploadedImageId = given()
                    .multiPart(multiPartSpecFileBase64)
                    .multiPart(multiPartSpecFileType)
                    .multiPart(multiPartSpecFileTitle)
                    .expect()
                    .spec(responseSpecBodyFileType)
                    .spec(responseSpecBodyFileTitle)
                    .spec(responseSpecBodyId)
                    .spec(responseSpecBody)
                    .when()
                    .post("/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @DisplayName("Загрузка imageBase64 с параметром Type")
        @Test
        void uploadFileImageBase64WithTypeTest() {
            uploadedImageId = given()
                    .multiPart(multiPartSpecFileBase64)
                    .multiPart(multiPartSpecFileType)
                    .expect()
                    .spec(responseSpecBodyFileType)
                    .spec(responseSpecBodyFileTitleIsNull)
                    .spec(responseSpecBodyId)
                    .spec(responseSpecBody)
                    .when()
                    .post("/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }

        @DisplayName("Загрузка imageBase64 с параметром Title")
        @Test
        void uploadFileImageBase64WithTitleTest() {
            uploadedImageId = given()
                    .multiPart(multiPartSpecFileBase64)
                    .multiPart(multiPartSpecFileTitle)
                    .expect()
                    .spec(responseSpecBodyFileType)
                    .spec(responseSpecBodyFileTitle)
                    .spec(responseSpecBodyId)
                    .spec(responseSpecBody)
                    .when()
                    .post("/image")
                    .prettyPeek()
                    .then()
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("data.deletehash");
        }
    }

    @DisplayName("Апдейт image title и description")
    @Test
    void updateImageInfo() {
        uploadedImageIdForUpdate = given()
                .multiPart(multiPartSpecFile)
                .expect()
                .spec(responseSpecBodyFileType)
                .spec(responseSpecBodyFileTitleIsNull)
                .spec(responseSpecBodyId)
                .spec(responseSpecBody)
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");

        System.out.println(uploadedImageIdForUpdate);

        given()
                .multiPart("title", titleForUpdate)
                .multiPart("description", descriptionForUpdate)
                .log().all()
                .expect()
                .body("data", equalTo(true))
                .spec(responseSpecBody)
                .when()
                .post("/image/{uploadedImageIdForUpdate}", uploadedImageIdForUpdate)
                .prettyPeek()
                .then();
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
