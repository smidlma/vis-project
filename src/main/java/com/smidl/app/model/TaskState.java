package com.smidl.app.model;

public enum TaskState {
    NEW("New"), RUNNING("In progress"), REVISION("In revision"), DONE("Done");

    private String code;

    private TaskState(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
