package com.redcoolhax.todocli;

/**
 * Represents the current progress of a Task (see: com.redcoolhax.todocli.Task).
 */
public enum TaskStatus {
    /**
     * Status of a Task that hasn't been started.
     */
    NOT_STARTED("Not started"),

    /**
     * Status of a Task that is currently in progress.
     */
    IN_PROGESS("In progress"),

    /**
     * Status of a Task that has been finished.
     */
    FINISHED("Finished");

    private String stringVer;

    /**
     * Creates a new TaskStatus, for which a String equivalent is given 
     * (ie "Not started" for TaskStatus.NOT_STARTED).
     * @param stringVer
     */
    private TaskStatus(String stringVer) {
        this.stringVer = stringVer;
    }

    @Override
    public String toString() {
        return stringVer;
    }
}