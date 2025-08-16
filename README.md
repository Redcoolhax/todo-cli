# To-Do CLI App

## Description and Features
A simple to-do CLI Java app I made as an exercise and for the purpose of receiving feedback. Allows the user to create, modify, delete, mark, and arrange tasks within individual 'task lists' specified by the user. These task lists can be loaded from and saved to json files for data persistence.

## Core Dependencies
- Java 17
- Maven

## Libraries Used (Handled by Maven)
- Jackson (Json serialization libary)

## How to Run
```bash
mvn compile
mvn exec:java
```

## Additional Configuration (Optional)
The program stores the task lists in a folder specified in the config.properties file by the 'dataFolder' setting. This is ./data by default. If the specified folder doesn't already exist, then it will be automatically created upon starting the program.

## License
This project is licensed under the [MIT License](LICENSE) © 2025 Redcoolhax.