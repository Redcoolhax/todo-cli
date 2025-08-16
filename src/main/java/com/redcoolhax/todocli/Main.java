package com.redcoolhax.todocli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Main {
    private static final String DEFAULT_DATA_FOLDER = "./data";

    public static void main(String[] args) {
        File dataFolder;
        try {
            dataFolder = getDataDirectory();
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(
                "\nSorry! The data folder couldn't be accessed. " +
                "See the above error message for more details."
            );
            return;
        }
        
        System.out.println(
            "Welcome to the to-do CLI app!\n" +
            "To begin, enter the name of one of the following task lists, " +
            "or enter a new name to create a new one."
        );
        printTaskListsInFolder(dataFolder);
        System.out.println();

        Scanner input = new Scanner(System.in);
        String taskListName = input.nextLine();

        List<Task> tasks;
        try {
            tasks = new ArrayList<Task>(Arrays.asList(
                loadFromJson(dataFolder.getName() + "/" + taskListName + ".json")
                ));
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
            saveToJson(dataFolder.getName() + "/" + taskListName + ".json",
                tasks.toArray(new Task[tasks.size()]));
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


    // METHODS FOR LOADING OTHER TYPES OF DATA

    /**
     * Gets the dataFolder as specified in config.properties. If config.properties cannot be read, then 
     * the default of ./data will be used instead. In either case, if the data folder doesn't exist, then 
     * it will be created. The user will also be notified via the console if the folder is successfully 
     * loaded/created or not.
     * @return The data folder as specified by 'dataFolder' in config.properties, or the ./data folder if 
     * the aforementioned file can't be read.
     * @throws RuntimeException if the folder doesn't already exist and can't be created.
     */
    private static File getDataDirectory() {
        Properties properties = new Properties();
        File dataFolder;
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            String dataFolderName = properties.getProperty("dataFolder");
            System.out.println("Using folder: " + dataFolderName);
            dataFolder = new File(dataFolderName);
        } catch (IOException e) {
            System.out.println(
                "There was an error loading the selected dataFolder from config.properties, " +
                "using default: " + DEFAULT_DATA_FOLDER
            );
            dataFolder = new File(DEFAULT_DATA_FOLDER);
        }

        if (!dataFolder.exists()) {
            boolean successfullyCreated = dataFolder.mkdirs();
            if (!successfullyCreated) {
                throw new RuntimeException("Couldn't create data folder: " + dataFolder.getName());
            } else {
                System.out.println("Creating new folder: " + dataFolder.getName());
            }
        }

        return dataFolder;
    }

    
    // METHODS FOR SHOWING INFO TO THE USER.

    /**
     * Prints out the names of all json files in a provided directory.
     * The json extension itself is excluded, as the user loads task lists by inputting 
     * the file name without it.
     * @param folder The directory being searched for json files.
     */
    private static void printTaskListsInFolder(File folder) {
        File[] files = folder.listFiles();

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