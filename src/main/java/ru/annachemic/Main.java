package ru.annachemic;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.annachemic.db.dao.CategoriesMapper;
import ru.annachemic.db.model.Categories;
import ru.annachemic.db.model.CategoriesExample;

import java.io.IOException;

@Slf4j
public class Main {
    static Faker faker = new Faker();
    private static String resource = "mybatisConfig.xml";

    public static void main(String[] args) throws IOException {
        SqlSession sqlSession = getSqlSession(); // открываем сессию, открыв экземпляр класса SqlSession

        CategoriesMapper categoriesMapper = sqlSession.getMapper(CategoriesMapper.class);

        // метод, который считает кол-во категорий
        long categoriesCount = categoriesMapper.countByExample(new CategoriesExample());
        log.info(String.valueOf(categoriesCount));
    }

    public static SqlSession getSqlSession() throws IOException {
        SqlSessionFactory sqlSessionFactory;

        // создаем драйвер, который будет работать с нашей БД
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        return sqlSessionFactory.openSession(true);
    }
}
