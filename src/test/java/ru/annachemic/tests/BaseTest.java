package ru.annachemic.tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import retrofit2.Retrofit;
import ru.annachemic.db.dao.ProductsMapper;
import ru.annachemic.dto.Product;
import ru.annachemic.service.CategoryService;
import ru.annachemic.service.ProductService;
import ru.annachemic.utils.DbUtils;
import ru.annachemic.utils.RetrofitUtils;

public class BaseTest {
    static ProductsMapper productsMapper;
    static Retrofit client; // создаем объект типа Retrofit, в котором будет лежать client
    static ProductService productService; // создаем объект ProductService, который мы описали
    static CategoryService categoryService; // создаем объект CategoryService, который мы описали
    Faker faker = new Faker(); // тестовые данные, которые вызываются в случаенном порядке

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class); // создаем ProductService
        categoryService = client.create(CategoryService.class);
        productsMapper = DbUtils.getProductsMapper();
    }
}
