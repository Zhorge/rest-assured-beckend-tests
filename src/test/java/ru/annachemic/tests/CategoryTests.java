package ru.annachemic.tests;

import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.annachemic.dto.Category;
import ru.annachemic.enums.CategoryType;
import ru.annachemic.utils.PrettyLogger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CategoryTests extends BaseTest {

    @SneakyThrows
    @Test
    void getCategoryByIdTest() {
        Integer id = CategoryType.ELECTRONIC.getId();

        Response<Category> response = categoryService
                .getCategory(id)
                .execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getTitle(), equalTo(CategoryType.ELECTRONIC.getTitle()));
        assertThat(response.body().getId(), equalTo(id));
    }
}
