# Отчет о лаботаротоной работе
# Лабораторная работа 7. Spring Security. Basic Authentication

## Цель работы
И1. Интеграция Spring Security в приложение
2. Настройка ролевого доступа (USER и MANAGER)
3. Реализация form-based аутентификации для веб-интерфейса
4. Настройка Basic Authentication для REST API
5. Тестирование системы безопасности

## Выполнение работы
1. Настройка окружения
Добавлены зависимости в build.gradle:
gradle
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'

2. Конфигурация безопасности
Создан класс SecurityConfig

3. Обновление веб-интерфейса
Добавлены элементы безопасности в Thymeleaf-шаблоны:

html
<div sec:authorize="isAuthenticated()">
    Добро пожаловать, <span sec:authentication="name"></span>!
    <form th:action="@{/logout}" method="post">
        <button type="submit">Выйти</button>
    </form>
</div>

4. Тестирование
Проверены сценарии:
Доступ USER: только просмотр заказов
Доступ MANAGER: полное управление заказами
Basic Auth для API:
bash
curl -u manager:manager123 http://localhost:8080/api/orders

5. Сборка и деплой
Собрано WAR-приложение:

bash
gradle war
Развернуто на Apache Tomcat 11 через Manager UI

## Выводы
Успешно интегрирована система безопасности Spring Security
Реализована аутентификация через форму для веб-интерфейса
Настроена Basic Authentication для REST API
Реализовано разграничение прав доступа:
    USER - только просмотр
    MANAGER - полный доступ
Протестирована работа системы безопасности
Приложение корректно работает после деплоя на Tomcat
Сохранена вся функциональность с предыдущих лабораторных работ
