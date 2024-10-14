# EasyText API

EasyText API is an API for creating and managing text in Minecraft using Fabric. It allows developers to easily add styles, gradients, and other effects to text objects in the game.

## Features

- Simple creation of text objects with formatting.
- Support for gradients and HEX colors.
- Handling of click and hover events.
- Extensible tag system for formatting.

## Installation

1. Download [easytextapi-1.0.jar](https://github.com/greentea1337/EasyTextAPI/releases/download/pre-release/easytextapi-1.0.jar) (or build the project yourself).
2. Place `easytextapi-1.0.jar` in the `mods` folder of your Minecraft server.

To add the dependency to your project, include the following in your `build.gradle` file:

```groovy
modImplementation files('c:/path/easytextapi-1.0.jar')
```

## Usage

### Creating Texts

#### Simple Text

```java
Text simpleText = Easytext.createSimpleText("Hello, world!");
```

#### Styled Text

```java
Text styledText = Easytext.createStyledText("Styled text", Formatting.BOLD, Formatting.ITALIC);
```

#### Gradient Text

```java
Text gradientText = Easytext.createGradientText("Gradient text", "#FF0000", "#0000FF");
```

#### Tagged Text

```java
Text taggedText = Easytext.createTaggedText("<bold>Bold text</bold> <color=red>Red text</color>");
```

### Handling Events

EasyText API also supports handling click and hover events:

```java
Text clickableText = Easytext.createTaggedText("<click action='run_command' value='/say Hello!'>Click me!</click>");
```

## Contributing

If you would like to contribute, please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the MIT License. Please see the [LICENSE.txt](LICENSE.txt) file for more information.
