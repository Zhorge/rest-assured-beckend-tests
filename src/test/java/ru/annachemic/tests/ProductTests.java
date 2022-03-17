package ru.annachemic.tests;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.annachemic.dto.Product;
import ru.annachemic.enums.CategoryType;
import ru.annachemic.utils.PrettyLogger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class ProductTests extends BaseTest {

    Integer id;
    Product productForUpdate;

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.beer().name())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.ELECTRONIC.getTitle());
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService
                .deleteProduct(id)
                .execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @SneakyThrows
    @Test
    void createProductTest() {
        Response<Product> response = productService
                .createProduct(product)
                .execute();

        id = response.body().getId();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));

        Response<Product> responseGetProduct = productService
                .getProduct(id)
                .execute();

        assertThat(responseGetProduct.isSuccessful(), CoreMatchers.is(true));
        assertThat(responseGetProduct.body().getTitle(), equalTo(product.getTitle()));
        assertThat(responseGetProduct.body().getPrice(), equalTo(product.getPrice()));
        assertThat(responseGetProduct.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
    }

    @SneakyThrows
    @Test
    void fullUpdateProductTest() {
        Response<Product> response = productService
                .createProduct(product)
                .execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));

        id = response.body().getId();

        productForUpdate = new Product()
                .withId(id)
                .withTitle(faker.food().sushi())
                .withPrice((int) ((Math.random() + 1) * 1000))
                .withCategoryTitle(CategoryType.FOOD.getTitle());

        Response<Product> responseAfterUpdate = productService
                .updateProduct(productForUpdate)
                .execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(responseAfterUpdate.body().getTitle(), not(product.getTitle()));
        assertThat(responseAfterUpdate.body().getPrice(), not(product.getPrice()));
        assertThat(responseAfterUpdate.body().getCategoryTitle(), not(product.getCategoryTitle()));

        Response<Product> responseGetProduct = productService
                .getProduct(id)
                .execute();

        assertThat(responseGetProduct.isSuccessful(), CoreMatchers.is(true));
        assertThat(responseGetProduct.body().getTitle(), equalTo(productForUpdate.getTitle()));
        assertThat(responseGetProduct.body().getPrice(), equalTo(productForUpdate.getPrice()));
        assertThat(responseGetProduct.body().getCategoryTitle(), equalTo(productForUpdate.getCategoryTitle()));
    }
}
