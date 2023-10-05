# Project-for-heads-and-hands

## Ник в Telegram: [@SergeiBrin](https://t.me/sergeibrin)  

## EDT схема
![Cхема](https://github.com/SergeiBrin/project-for-heads-and-hands/blob/main/hero-vs-monsters.png)

## Описание
Project-for-heads-and-hands - игра, где герою предстоит сразиться с силами зла. 
Победит тот, кто окажется сильнее и удачливее.

## Установка
1. Клонировать проект с GitHub;
2. В pgAdmin 4 создать базу данных **{hero-vs-monsters}**;
3. Открыть проект в IntelijIdea;
4. В application.properties указать данные для таблицы: username={ваш логин}, password={ваш пароль};
5. Запустить ProjectForHeadsAndHandsApplication.

## Основные функции
1. **Создать новую игру:**  
```
POST http://localhost:8080/play/new-game  
BODY { "name": "имя героя" }
```
RequestParam:  
• type={трус, балбес, бывалый} - уровень героя.  
• comp={easy, medium, hard] - уровень монстров.  
• count={положительное число} - количество монстров.  

Пример запроса:  
```
POST http://localhost:8080/play/new-game?type=бывалый&comp=medium&count=3  
BODY { "name": "Спаситель" }
```

Параметры запроса можно не указывать!  
• Если опустить type, то параметры героя будут рандомными.  
• Если опустить comp, то параметры монстров будут рандомными.  
• Если опустить count, то количество монстров будет равно 2.  

2. **Создать новый бой:**  
```
POST http://localhost:8080/play/battle/{heroId}/{monsterId}
```
3. **Воскресить героя на 30% от начального уровня здоровья:**  
```
PATCH http://localhost:8080/hero/recovery/{heroId}
```
4. **Получить статистику по герою:**
```
GET http://localhost:8080/statistics/{heroId}
```
## Основные технологии
Java 17, Maven, Spring Boot 3.1.4, Postgres, Hibernate. 


