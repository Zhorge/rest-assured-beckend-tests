package ru.annachemic.utils;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.annachemic.db.dao.CategoriesMapper;
import ru.annachemic.db.dao.ProductsMapper;
import ru.annachemic.db.model.Categories;
import ru.annachemic.db.model.CategoriesExample;
import ru.annachemic.db.model.ProductsExample;

import java.io.IOException;

@UtilityClass
public class DbUtils {

    static Faker faker = new Faker();
    private static String resource = "mybatisConfig.xml";

    public static SqlSession getSqlSession() throws IOException { // метод, который подкл. нас к БД
        SqlSessionFactory sqlSessionFactory;
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        return sqlSessionFactory.openSession(true);
    }

    @SneakyThrows //метод, возвращающий маппер для таблички Categories
    public static CategoriesMapper getCategoriesMapper() {
        return getSqlSession().getMapper(CategoriesMapper.class);
    }

    @SneakyThrows //метод, возвращающий маппер для таблички Product
    public static ProductsMapper getProductsMapper() {
        return getSqlSession().getMapper(ProductsMapper.class);
    }

    //метод, который будет создавать для нас новую категорию
    private static void createNewCategory(CategoriesMapper categoriesMapper) {
        Categories newCategory = new Categories();
        newCategory.setTitle(faker.animal().name());

        categoriesMapper.insert(newCategory);
    }

    public static Integer countCategories(CategoriesMapper categoriesMapper) {
        long categoriesCount = categoriesMapper.countByExample(new CategoriesExample());
        return Math.toIntExact(categoriesCount);
    }

    public static Integer countProducts(ProductsMapper productsMapper) {
        long productsCount = productsMapper.countByExample(new ProductsExample()); // кол-во продуктов
        return Math.toIntExact(productsCount); // функция, которая конвертирует в int
    }
}
