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
            "To begin, enter the name of one of the following task lists, " +
            "or enter a new name to create a new one."
        );
        printTaskListsInCurrentFolder();
        System.out.println();

        Scanner input = new Scanner(System.in);
        String taskListName = input.nextLine();

        List<Task> tasks;
        try {
            tasks = Arrays.asList(loadFromJson(taskListName + ".json"));
            System.out.println("Successfully loaded " + taskListName + "!");
        } catch (IOException e) {
            tasks = new ArrayList<>();
            System.out.println(
                "This task list doesn't exist yet. The task list will be saved there once you're done."
            );
        }
        
        new UserTasksManager(tasks, input).run();
        input.close();

        try {
            saveToJson(taskListName + ".json", tasks.toArray(new Task[tasks.size()]));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(
                "\nSorry! Your tasks couldn't be saved. " +
                "See the above error message for more details."
            );
        }
    }


    // METHODS FOR LOADING AND SAVING JSON FILES.

    /**
     * Loads an array of Tasks from a given file path leading to a json file.
     * @param path Path to the json file.
     * @return An array of Tasks as provided by the json file.
     * @throws IOException If the provided path can't be read (ie file doesn't exist).
     */
    private static Task[] loadFromJson(String path) throws IOException {
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
    private static void saveToJson(String path, Task[] tasks) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(path), tasks);
    }

    
    // METHODS FOR SHOWING INFO TO THE USER.

    /**
     * Prints out the names of all json files in the current working directory.
     * The json extension itself is excluded, as the user loads task lists by inputting 
     * the file name without it.
     */
    private static void printTaskListsInCurrentFolder() {
        File currentFolder = new File(".");
        File[] files = currentFolder.listFiles();

        for (File file : files) {
            if (file.isDirectory())
                continue;

            String fileName = file.getName();
            
            if (extensionOfFileName(fileName).equals("json"))
                System.out.println(fileNameWithoutExtension(fileName));
        }
    }

    
    // METHODS FOR PARSING INFO FROM FILE NAMES.

    /**
     * @param fileName The file name or path of a file.
     * @return The file name without its extension (or just the file name if it doesn't have one).
     */
    private static String fileNameWithoutExtension(String fileName) {
        int indexOfLastPeriod = fileName.lastIndexOf('.');
        if (indexOfLastPeriod == -1)
            return fileName;
        return fileName.substring(0, indexOfLastPeriod);
    }

    /**
     * @param fileName The file name or path of a file.
     * @return The file's extension (or an empty String if it doesn't have one).
     */
    private static String extensionOfFileName(String fileName) {
        int indexOfLastPeriod = fileName.lastIndexOf('.');
        if (indexOfLastPeriod == -1)
            return "";
        return fileName.substring(indexOfLastPeriod + 1, fileName.length());
    }
}