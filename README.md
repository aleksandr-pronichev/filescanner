# Сервис сигнатурного сканирования файлов (Java + Spring Boot)

## Описание проекта
Этот сервис позволяет загружать файлы, сканировать на наличие известных вредоносных сигнатур, сохранять их в базе данных и получать по идентификатору или имени.
Проект использует Java, Spring Boot и реляционную базу данных PostgreSQL.

## Функциональность
- REST API для загрузки и скачивания файлов
- Хранение файлов и их сигнатур в базе данных
- Поддержка различных форматов файлов
- Сканирование на наличие вредоносных сигнатур через хеширование файлов.

## Используемый стек технологий
- Java 21
- Spring Boot
- PostgreSQL
- Hibernate
- Maven

## Примеры запросов к API

### Загрузка файла
**POST /api/files**

### Получение файла по ID
**GET /api/files/{id}**

### Получение файла по имени
**GET /api/files/by-name/{name}**  


