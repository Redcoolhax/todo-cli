package com.redcoolhax.todocli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        List<Task> tasks;
        try {
            tasks = Arrays.asList(loadFromJson("test.json"));
        } catch (IOException e) {
            tasks = new ArrayList<>();
        }

        Scanner input = new Scanner(System.in);
        new UserTasksManager(tasks, input).run();
        input.close();

        try {
            saveToJson("test.json", tasks.toArray(new Task[tasks.size()]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Task[] loadFromJson(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Task[] tasks = mapper.readValue(new File(path), Task[].class);
        return tasks;
    }

    public static void saveToJson(String path, Task[] tasks) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path), tasks);
    }
}