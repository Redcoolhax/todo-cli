# To-Do CLI App

## Description and Features
A simple to-do CLI Java app I made as an exercise and for the purpose of receiving feedback. Allows the user to create, modify, delete, mark, and arrange tasks within individual 'task lists' specified by the user. These task lists can be loaded from and saved to json files for data persistence.

See: [config.properties](config.properties) for additional configuration settings.

## Core Dependencies
- Java 17
- Maven

## Libraries Used (Handled by Maven)
- Jackson (Json serialization libary)

## How to Run
When building from source, the easiest way is to use the exec-maven-plugin. These two commands will compile the Java files into class files, and then run the application with dependencies included:
```bash
mvn compile
mvn exec:java
```

Alternatively, you may use the maven-assembly-plugin, which is included to assist with the creation of release versions. This creates a jar file with all dependencies included, allowing it to run as long as the user has Java 17. To test this, build and run the jar with the following commands:
```bash
mvn package
java -jar target/todo-cli-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## License
This project is licensed under the [MIT License](LICENSE) © 2025 Redcoolhax.