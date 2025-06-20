# Отчет о лаботаротоной работе
# Лабораторная работа 6. Разработка Web-приложений с использованием Spring MVC

## Цель работы
1. Переход с сервлетов на Spring MVC
2. Реализация полноценного REST API для работы с заказами
3. Интеграция шаблонизатора Thymeleaf
4. Создание современного веб-интерфейса
5. Тестирование API с помощью Postman
## Выполнение работы

1. Подготовка окружения

Установлен Apache Tomcat 11
Добавлен пользователь с правами администратора в tomcat-users.xml:

xml
<user username="admin" password="admin" roles="manager-gui,admin-gui"/>
Настроена сборка WAR-файла в build.gradle:

gradle
plugins {
    id 'war'
}
war {
    archiveFileName = 'zoo-shop.war'
}

2. Реализация сервлетов

2.1 Список заказов (OrderListServlet)
Отображает таблицу с:

ID заказа
Данными клиента
Общей суммой
Статусом
Кнопка "Создать заказ" для перехода к форме

2.2 Форма заказа (OrderFormServlet)

java
@WebServlet("/create-order")
public class OrderFormServlet extends HttpServlet {
    // Реализация формы выбора товаров
}
2.3 REST API (ProductApiServlet)
Возвращает JSON:

json
[
  {
    "name": "Корм для собак",
    "category": "Корма",
    "stock": 50
  }
]

3. Тестирование

Postman-запрос к REST API:

http
GET http://localhost:8080/zoo-shop/api/products
Валидация данных формы заказа

Проверка корректности отображения списка заказов

4. Развертывание
Сборка:

bash
gradle war
Деплой zoo-shop.war через Tomcat Manager

## Выводы 
Успешно выполнена миграция с сервлетов на Spring MVC
Реализовано полноценное REST API для работы с заказами
Создан современный веб-интерфейс с использованием Thymeleaf
Настроена двусторонняя связь между фронтендом и бэкендом
Протестирована работа API с помощью Postman
Приложение корректно работает после деплоя на Tomcat
Сохранена вся бизнес-логика и расширена функциональность
