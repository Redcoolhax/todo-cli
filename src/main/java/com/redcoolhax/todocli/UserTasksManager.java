package com.redcoolhax.todocli;

import java.util.List;
import java.util.Scanner;

public class UserTasksManager {
    private List<Task> tasks;
    private Scanner input;

    /**
     * Creates a new UserTasksManager
     * @param tasks List of Tasks that this UserTasksManager will be working with.
     * @param input Scanner that takes in the user's input.
     */
    public UserTasksManager(List<Task> tasks, Scanner input) {
        this.tasks = tasks;
        this.input = input;
    }

    public void run() {
        String response;

        for (;;) {
            viewTasks();
            System.out.println(
                "Please select an option:\n" +
                "1: Add a new task\n" +
                "2: Change status of task\n" +
                "3: Remove a task\n" +
                "4: Move task\n" +
                "5: Change description of task\n" +
                "6: Exit\n"
            );

            switch (input.nextLine()) {
                case "1" -> addTask();

            }
        }
    }

    private void viewTasks() {
        System.out.println("---List of tasks:---");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("------");
    }

    private void addTask() {
        System.out.print("Description of new task: ");
        String description = input.nextLine();
        tasks.addLast(new Task(description));
    }
}