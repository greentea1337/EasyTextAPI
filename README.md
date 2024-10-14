
# EasyText API

EasyText API — это API для создания и управления текстом в Minecraft с использованием Fabric. Он позволяет разработчикам легко добавлять стили, градиенты и другие эффекты к текстовым объектам в игре.

## Особенности

- Простое создание текстовых объектов с помощью форматирования.
- Поддержка градиентов и HEX-цветов.
- Обработка кликовых и навигационных событий.
- Расширяемая система тегов для форматирования.

## Установка

1. Скачайте [easytextapi-1.0.jar](https://github.com/greentea1337/easytextapi/releases/latest/download/easytextapi-1.0.jar) (или соберите проект самостоятельно).
2. Поместите `easytextapi-1.0.jar` в папку `mods` вашего Minecraft сервера.

Для добавления зависимости в ваш проект, добавьте следующее в файл `build.gradle`:

```groovy
modImplementation files('c:/path/easytextapi-1.0.jar')
```

## Использование

### Создание текстов

#### Простой текст

```java
Text simpleText = Easytext.createSimpleText("Привет, мир!");
```

#### Стилизованный текст

```java
Text styledText = Easytext.createStyledText("Стилизованный текст", Formatting.BOLD, Formatting.ITALIC);
```

#### Текст с градиентом

```java
Text gradientText = Easytext.createGradientText("Градиентный текст", "#FF0000", "#0000FF");
```

#### Текст с тегами

```java
Text taggedText = Easytext.createTaggedText("<bold>Жирный текст</bold> <color=red>Красный текст</color>");
```

### Обработка событий

EasyText API также поддерживает обработку кликов и навигационных событий:

```java
Text clickableText = Easytext.createTaggedText("<click action='run_command' value='/say Привет!'>Нажми на меня!</click>");
```

## Участие в проекте

Если вы хотите внести свой вклад, пожалуйста, создайте форк репозитория и отправьте пулл-реквест с вашими изменениями.

## Лицензия

Этот проект лицензирован под лицензией MIT. Пожалуйста, смотрите файл [LICENSE.txt](LICENSE.txt) для получения подробной информации.
