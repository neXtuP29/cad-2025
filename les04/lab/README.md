# Отчет о лаботаротоной работе
# Лабораторная работа 2. Конфигурирование приложения Spring с помощью аннотаций. Применение АОП для логирования

## Цель работы
1. Переход на аннотационное конфигурирование Spring-приложения
2. Добавление функционала вывода таблиц в HTML-формате
3. Измерение времени выполнения методов с помощью АОП
4. Освоение работы с событиями жизненного цикла бинов
## Выполнение работы

### 1. Аннотационное конфигурирование
Заменяем XML-конфигурацию на аннотации:
- `@Component` для объявления бинов
- `@Autowired` для внедрения зависимостей
- `@Value` для чтения значений из properties-файла

### 2. Работа с application.properties
Добавлен файл `src/main/resources/application.properties`:
```properties
products.file.path=data/products.csv

### 3. HTML Table Renderer
Реализован HTMLTableRenderer:

@Component
public class HTMLTableRenderer implements Renderer {
    // Реализация вывода в HTML
}

### 4. События жизненного цикла
Для ResourceFileReader добавлен метод с аннотацией @PostConstruct:

@PostConstruct
public void init() {
    System.out.println("ResourceFileReader initialized at: " + LocalDateTime.now());
}

### 5. АОП для замера времени
Создан аспект для логирования времени выполнения:


@Aspect
@Component
public class PerformanceAspect {
    @Around("execution(* com.example.readers.*.read(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        System.out.println("Method " + joinPoint.getSignature() + " executed in " + duration + "ms");
        return result;
    }
}

## Выводы
Освоено аннотационное конфигурирование Spring-приложения

Реализовано чтение конфигурации из properties-файла

Добавлен новый рендерер для вывода данных в HTML-формате

Изучены события жизненного цикла бинов Spring

Применены аспекты Spring AOP для логирования времени выполнения методов

Упрощена конфигурация приложения за счет использования аннотаций