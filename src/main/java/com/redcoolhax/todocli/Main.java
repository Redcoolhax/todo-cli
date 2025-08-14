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
        System.out.println(
            "Welcome to the to-do CLI app!\n" +
            "To begin, enter the name/path to a valid json file (extension included).\n" +
            "Alternatively, enter a name for a new json file, which will be created at " +
            "the conclusion of this program."
        );
        Scanner input = new Scanner(System.in);
        String jsonFilePath = input.nextLine();

        List<Task> tasks;
        try {
            tasks = Arrays.asList(loadFromJson(jsonFilePath));
            System.out.println("Successfully loaded from a pre-existing json file!");
        } catch (IOException e) {
            tasks = new ArrayList<>();
            System.out.println(
                "This file doesn't exist yet. The task list will be saved there once you're done."
            );
        }
        
        new UserTasksManager(tasks, input).run();
        input.close();

        try {
            saveToJson(jsonFilePath, tasks.toArray(new Task[tasks.size()]));
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