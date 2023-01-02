# Microservices

## Основной функционал

Проект реализован на основе тестовое задания для backend developer(см. в конце)
 
В данном репозитории реализован функционал трех микросервисов, написанных с использованием Spring Boot, которые взаимодействуют между собой через передачу сообщений. Сервисы и окружения запускаются в контейнерах с использованием docker-compose.
Каждый сервис добавляет дату и время после получения сообщения.

## Детали реализации

- После отправки GET запроса /start на ms1 начинается генерация сообщений и отправка их на ms2 до истечения interaction.time или отправки GET запроса /stop на ms1, после которого выводится информация о количестве отправленных сообщений и времени работы сервисов.
- Ms1 передает сообщения ms2 через WebSocket. Сервер (ms2) и клиент (ms1) сконфигурированы с помощью Spring Web.
- Ms2 передает сообщения ms3 через брокера сообщений Kafka сконфигурированных с помощью Spring Kafka
- Ms3 передает сообщения ms1 через HTTP запрос. Ms3 клиент сконфигурирован с помощью Spring Web, сервер ms1 с помощью Spring MVC.
- Ms1 сохраняет сообщения в БД.

## Запуск

- Структура каталогов должна быть как в репозитории.
- Упаковать сервисы в jar архивы (maven instal в Intellij IDEA)
- Запустить docker-compose 
- В терминале mc1 выполнить команду 'curl http://localhost:8080/start'


## Тестовое задание для backend developer

Создать три взаимодействующих между собой микросервиса МС1, МС2 и МС3.
Микросервисы взаимодействую между собой следующим образом:


```JS
{
id: integer.
“session_id”: integer,
“MC1_timestamp”: datetime,
“MC2_timestamp”: datetime,
“MC3_timestamp”: datetime,
“end_timestamp”: datetime
}
```

1) МС1 создает сообщение следующего формата(см. выше): “session_id” - номер сеанса взаимодействия; МС1 записывает в поле “MC1_timestamp” текущее время и отправляет сообщение в МС2 через WebSocket.
2) МС2 принимает сообщение от МС1, записывает в поле сообщения “МС2_timestamp” текущее время и отправляет сообщение в МС3 через топик брокера Kafka;
3) МС3 принимает сообщение от МС2, записывает в поле сообщения “МС3_timestamp” текущее время и отправляет сообщение в МС1 посредством отправки http запроса POST с телом, содержащим сообщение;
4) МС1 принимает сообщение от МС3, записывает в поле “end_timestamp” текущее время, записывает сообщение в базу данных;
5) Повторить цикл взаимодействия в течение заданного интервала взаимодействия. 

Длительность интервала взаимодействия задается в секундах параметром в конфигурационном файле. В качестве БД использовать СУБД MariaDB. После остановки контейнеров с микросервисами и окружением база данных должна быть доступна для просмотра средствами СУБД. Запуск микросервисов и окружения производить в docker-compose. Старт взаимодействия осуществить отправкой запроса GET на /start/ без параметров в МС1. Досрочную остановку взаимодействия осуществить отправкой запроса GET на /stop/ без параметров в МС1. Начало взаимодействия микросервисов индицировать на консоль. Завершение взаимодействия индицировать на консоль с выводом следующих параметров:

1) время взаимодействия;
2) количество сообщений, сгенерированных во время взаимодействия.

