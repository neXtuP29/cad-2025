# Отчет о лаботаротоной работе
# Лабораторная работа 5. Разработка и развертывание Web-приложений

## Цель работы
1. Развертывание Apache Tomcat 11
2. Создание WAR-артефакта
3. Разработка веб-интерфейса для работы с заказами
4. Реализация REST API для продуктов
5. Деплой приложения на сервер приложений
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

## Вывод
Успешно настроен и развернут Apache Tomcat 11
Приложение собрано в WAR-артефакт и деплоено на сервер
Реализован веб-интерфейс для просмотра и создания заказов
Создан REST API для получения информации о продуктах
Протестирована работа API с помощью Postman
Приложение корректно отображает страницы и обрабатывает запросы
Сохранена вся бизнес-логика из предыдущих лабораторных работ
