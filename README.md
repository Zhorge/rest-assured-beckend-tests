# **Retrofit backend tests**

**Тестируем** - http://80.78.248.82:8189/market/swagger-ui.html#/


**Тесты:**
- **BaseTest** - в этом классе находится метод `beforeAll` с аннатацией `@BeforeAll`

- **CategoryTests** - Тесты для "category-controller". В этом классе находится `@Test getCategoryByIdTest`, который проверяет Get запрос

- **ProductTests** - Тесты для "product-controller". В этом классе находится метод`@BeforeEach
  void setUp()`, который перед каждым тестом инициализирует объект "product", метод `"@AfterEach
  void tearDown()`, который удаляет созданный при помощи POST запроса объект "product".
  - `@Test
    void createProductTest() {` 
    - создает объект при помощи POST запроса, 
    - логгирует ответ, 
    - проверяет ответ на наличие отправленных параметров в теле, 
    - проверяет, что в базе данных кол-во продуктов увеличилось на 1
    - проверяет, при помощи GET запроса, что объект создан
  - `@Test
    void fullUpdateProductTest() { ` 
    - создает объект, 
    - изменяет параметры в боди и проверяет, что старый объект принял новые параметры