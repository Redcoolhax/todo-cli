package com.redcoolhax.todocli;

/**
 * Represents a Task within the to-do app. 
 * Has data for a title, description, and status representing the current progress on said Task.
 */
public class Task {
    private String description;
    private TaskStatus status;

    /**
     * Creates a new Task.
     * @param description This Task's description.
     * @param status This Task's status representing its current progress.
     */
    public Task(String description, TaskStatus status) {
        this.description = description;
        this.status = status;
    }

    /**
     * Creates a new Task whose status is the default value (TaskStatus.NOT_STARTED).
     * @param description This Task's description.
     */
    public Task(String description) {
        this(description, TaskStatus.NOT_STARTED);
    }

    /**
     * Empty constructor for compatibility with 
     * com.fasterxml.jackson.databind.ObjectMapper's readValue method.
     */
    public Task() {}

    /**
     * @return This Task's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description This Task's description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return This Task's status representing its current progress.
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * @param status This Task's status representing its current progress.
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\"" + description + "\" : " + status;
    }
}