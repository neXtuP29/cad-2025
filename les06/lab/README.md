# Отчет о лаботаротоной работе
# Лабораторная работа 3. Технологии работы с базами данных. JDBC

## Цель работы
1. Интеграция встроенной базы данных H2 в приложение
2. Освоение работы с JDBC в Spring
3. Реализация сохранения данных в БД
4. Настройка выполнения SQL-запросов и их логирования
5. Расширение функциональности приложения новыми сущностями
## Выполнение работы
### 1. Настройка базы данных H2

Добавлены зависимости в `build.gradle.kts`:
```kotlin
implementation("com.h2database:h2:2.2.224")
implementation("org.springframework.boot:spring-boot-starter-jdbc:3.2.0")

### 2. SQL скрипты инициализации
Создан файл src/main/resources/schema.sql:

CREATE TABLE CATEGORIES (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500)
);

CREATE TABLE PRODUCTS (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES CATEGORIES(id)
);

### 3. Конфигурация DataSource

@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            .build();
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

### 4. Модель Category

public class Category {
    private Long id;
    private String name;
    private String description;
    // геттеры и сеттеры
}

### 5. DataBaseRenderer
java
@Component
@Primary
public class DataBaseRenderer implements Renderer {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public DataBaseRenderer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void render(List<Product> products) {
        // Сохранение продуктов в БД
        String sql = "INSERT INTO PRODUCTS (id, name, price, category_id) VALUES (?, ?, ?, ?)";
        
        products.forEach(product -> {
            jdbcTemplate.update(sql, 
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategoryId());
        });
    }
}

### 6. CategoryRequest для выполнения запросов
java
@Component
public class CategoryRequest {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryRequest.class);
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public CategoryRequest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void logCategoriesWithMultipleProducts() {
        String sql = """
            SELECT c.id, c.name, COUNT(p.id) as product_count 
            FROM CATEGORIES c 
            JOIN PRODUCTS p ON c.id = p.category_id 
            GROUP BY c.id, c.name 
            HAVING COUNT(p.id) > 1
            """;
            
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            logger.info("Category: {}, Products count: {}", 
                rs.getString("name"),
                rs.getInt("product_count"));
            return null;
        });
    }
}


### 7. Настройка логирования (logback.xml)
xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
    </root>
</configuration>

## Выводы
Успешно интегрирована встроенная база данных H2 в Spring-приложение

Реализовано сохранение данных продуктов и категорий в БД

Освоена работа с JdbcTemplate для выполнения SQL-запросов

Настроено логирование результатов SQL-запросов с помощью Logback

Расширена функциональность приложения добавлением сущности "Категория"

Реализовано выполнение сложных SQL-запросов с соединением таблиц

Приложение корректно сохраняет данные в БД и выводит результаты запросов
