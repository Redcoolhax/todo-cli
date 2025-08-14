package com.redcoolhax.todocli;

import java.util.InputMismatchException;
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
        for (;;) {
            viewTasks();
            System.out.println(
                "\nPlease select an option:\n" +
                "1: Add a new task\n" +
                "2: Change status of task\n" +
                "3: Remove a task\n" +
                "4: Move task\n" +
                "5: Change description of task\n" +
                "6: Exit\n"
            );

            switch (input.nextLine()) {
                case "1" -> addTask();
                case "2" -> changeTaskStatus();
                case "3" -> removeTask();
                case "4" -> moveTask();
                case "6" -> {return;}
            }
        }
    }

    private void viewTasks() {
        System.out.println("\n\n---List of tasks:---");
        int i = 1;
        for (Task task : tasks) {
            System.out.println(i + ": " + task);
            i++;
        }
        System.out.println("------");
    }

    private void addTask() {
        System.out.print("Description of new task: ");
        String description = input.nextLine();
        tasks.addLast(new Task(description));
    }

    private void changeTaskStatus() {
        if (tasks.size() == 0) {
            System.out.println("Cannot change task status when there are no tasks.");
            return;
        }

        viewTasks();
        System.out.print("Index of Task to update status for: ");
        int index = adjustedIndexFromInput(tasks.size());

        if (index == -1)
            return;

        Task selectedTask = tasks.get(index);
        
        System.out.println(
            "Select the new status for this task:\n" +
            "1: Not started\n" +
            "2: In progress\n" +
            "3: Finished\n"
        );

        for (;;) {
            switch (input.nextLine()) {
                case "0" -> {return;}
                case "1" -> selectedTask.setStatus(TaskStatus.NOT_STARTED);
                case "2" -> selectedTask.setStatus(TaskStatus.IN_PROGESS);
                case "3" -> selectedTask.setStatus(TaskStatus.FINISHED);
                default -> {
                    printInvalidMenuSelectionMessage();
                    continue;
                }
            }
            return;
        }
    }

    private void removeTask() {
        if (tasks.size() == 0) {
            System.out.println("Cannot remove tasks when there are none.");
            return;
        }

        viewTasks();
        System.out.print("Index of Task to be removed: ");
        int index = adjustedIndexFromInput(tasks.size());

        if (index == -1)
            return;
        
        tasks.remove(index);
    }

    private void moveTask() {
        if (tasks.size() < 2) {
            System.out.println("Cannot move tasks when there are less than two.");
            return;
        }

        viewTasks();
        System.out.print("Index of Task to be moved: ");
        int currentIndex = adjustedIndexFromInput(tasks.size());
        if (currentIndex == -1)
            return;
        
        System.out.print("Index to move Task to: ");
        int newIndex = adjustedIndexFromInput(tasks.size());
        if (newIndex == -1)
            return;

        Task task = tasks.remove(currentIndex);
        tasks.add(newIndex, task);
    }

    private int adjustedIndexFromInput(int max) {
        int index;
        for (;;) {
            try {
                index = input.nextInt() - 1;
                input.nextLine();
            } catch (InputMismatchException e) {
                index = -1;
            }
            if (index >= -1 && index < max)
                return index;
            else
                System.out.println(
                    "\nInvalid input! Response must be an integer from 0 to "
                    + max
                    + ". Inputting 0 will return you to the menu."
                );
        }
    }

    private void printInvalidMenuSelectionMessage() {
        System.out.println(
            "Invalid input. Please select one of the available options "
            + "(or input 0 to return to menu)."
        );
    }
}