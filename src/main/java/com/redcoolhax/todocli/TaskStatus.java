package com.redcoolhax.todocli;

public enum TaskStatus {
    NOT_STARTED("Not started"),
    IN_PROGESS("In progress"),
    FINISHED("Finished");

    private String stringVer;

    private TaskStatus(String stringVer) {
        this.stringVer = stringVer;
    }

    @Override
    public String toString() {
        return stringVer;
    }
}