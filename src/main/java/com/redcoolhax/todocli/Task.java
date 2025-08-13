package com.redcoolhax.todocli;

/**
 * Represents a Task within the to-do app. 
 * Has data for a title, description, and status representing the current progress on said Task.
 */
public class Task {
    private String title;
    private String description;
    private TaskStatus status;

    /**
     * Creates a new Task.
     * @param title This Task's title.
     * @param description This Task's description.
     * @param status This Task's status representing its current progress.
     */
    public Task(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    /**
     * Creates a new Task whose status is the default value (TaskStatus.NOT_STARTED).
     * @param title This Task's title.
     * @param description This Task's description.
     */
    public Task(String title, String description) {
        this(title, description, TaskStatus.NOT_STARTED);
    }

    /**
     * @return This Task's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title This Task's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

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
}