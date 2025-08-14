package com.redcoolhax.todocli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        new UserTasksManager(tasks, input).run();
        input.close();
    }
}