package com.redcoolhax.todocli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Main {
    public static void main(String[] args) {
        List<Task> tasks;
        try {
            tasks = Arrays.asList(loadFromJson("test.json"));
        } catch (IOException e) {
            tasks = new ArrayList<>();
            e.printStackTrace();
        }

        Scanner input = new Scanner(System.in);
        new UserTasksManager(tasks, input).run();
        input.close();

        try {
            saveToJson("test.json", tasks.toArray(new Task[tasks.size()]));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(
                "\nSorry! Your tasks couldn't be saved. " +
                "See the above error message for more details."
            );
        }
    }

    /**
     * Loads an array of Tasks from a given file path leading to a json file.
     * @param path Path to the json file.
     * @return An array of Tasks as provided by the json file.
     * @throws IOException If the provided path can't be read (ie file doesn't exist).
     */
    public static Task[] loadFromJson(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Task[] tasks = mapper.readValue(new File(path), Task[].class);
        return tasks;
    }

    /**
     * Saves an array of Tasks to a given file path leading to a json file.
     * Creates one in said path if it doesn't already exist.
     * @param path Path to the json file.
     * @param tasks An array of Tasks to be saved to the json file.
     * @throws IOException If the provided path can't be written to.
     */
    public static void saveToJson(String path, Task[] tasks) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(path), tasks);
    }
}