package com.redcoolhax.todocli;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Class that, when run, has the user interface with a given set of Tasks via the console.
 * Allows for the viewing, addition, modification, removal, and arrangement of Tasks.
 */
public class UserTasksManager {
    private List<Task> tasks;
    private Scanner input;

    /**
     * Creates a new UserTasksManager
     * @param tasks List of Tasks that this UserTasksManager will be working with.
     * @param input Scanner that takes in the user's input via the console.
     */
    public UserTasksManager(List<Task> tasks, Scanner input) {
        this.tasks = tasks;
        this.input = input;
    }

    /**
     * Runs this UserTasksManager, allowing the user to interact with the respective set of Tasks 
     * until they exit via the command line. They choose from a set of options to select what 
     * operation they want to perform on the list, and can always return to this 
     * UserTasksManager's menu by inputting 0.
     */
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
                case "5" -> changeTaskDescription();
                case "6" -> {return;}
            }
        }
    }


    // METHODS CORRESPONDING TO MAIN MENU OPTIONS

    /**
     * Lets the user add a new Task via an inputted description.
     */
    private void addTask() {
        System.out.print("Description of new task: ");
        String description = input.nextLine();
        tasks.addLast(new Task(description));
    }

    /**
     * Lets the user change the status of a selected Task.
     */
    private void changeTaskStatus() {
        if (tasks.size() == 0) {
            System.out.println("Cannot change task status when there are no tasks.");
            return;
        }

        viewTasks();
        System.out.print("Index of task to update status for: ");
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

    /**
     * Lets the user remove a selected Task from the list.
     */
    private void removeTask() {
        if (tasks.size() == 0) {
            System.out.println("Cannot remove tasks when there are none.");
            return;
        }

        viewTasks();
        System.out.print("Index of task to be removed: ");
        int index = adjustedIndexFromInput(tasks.size());

        if (index == -1)
            return;
        
        tasks.remove(index);
    }

    /**
     * Lets the user move a selected Task to a new index.
     */
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

    /**
     * Lets the user change the description of a selected Task.
     */
    private void changeTaskDescription() {
        if (tasks.size() == 0) {
            System.out.println("Cannot change task description when there are no tasks.");
            return;
        }

        viewTasks();

        System.out.print("Index of task to update description for: ");
        int index = adjustedIndexFromInput(tasks.size());
        if (index == -1)
            return;
        
        Task selectedTask = tasks.get(index);

        System.out.println("Old description: " + selectedTask.getDescription());
        System.out.print("New description: ");
        String newDescription = input.nextLine();

        selectedTask.setDescription(newDescription);
    }


    // METHODS FOR PRINTING MESSAGES TO THE CONSOLE

    /**
     * Prints a list of all Tasks to the console.
     */
    private void viewTasks() {
        System.out.println("\n\n---List of tasks:---");
        int i = 1;
        for (Task task : tasks) {
            System.out.println(i + ": " + task);
            i++;
        }
        System.out.println("------");
    }

    /**
     * Prints a message to the console indicating that the user has input a selection not 
     * corresponding to any options within a menu (or 0, which always corresponds with 
     * wanting to return to the menu.)
     */
    private void printInvalidMenuSelectionMessage() {
        System.out.println(
            "Invalid input. Please select one of the available options "
            + "(or input 0 to return to menu)."
        );
    }


    // UTILITY METHODS

    /**
     * Lets the user input an index ranging from 1 to a given maximum. 
     * Since any input by the user should use 1-based indexing, the value 
     * returned by this function decrements the user's input by 1 to fit with the 
     * 0-based indexing used by Java. For example, the user inputting 2 would result 
     * in this function returning 1.
     * 
     * Alternatively, the user may input 0, which would cause this function to return -1. 
     * However, an input of 0 always corresponds to wanting to return to the menu. For this reason, 
     * The resulting value of -1 should always be explicity checked for when this method is used instead 
     * of being used as an index (which would just result in an IndexOutOfBoundsException anyway).
     * @param max The maximum amount that the user is allowed to 
     * directly input (with 1-based indexing in mind).
     * @return The number entered by the user decremented by 1.
     */
    private int adjustedIndexFromInput(int max) {
        if (max < 1) {
            throw new IllegalArgumentException("Max index must be one or greater.");
        }

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
}