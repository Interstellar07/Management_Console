package com.zaidisam.to_do;

public class ToDoModel {
   public String task,date,priority,key;
   public   int status;
    public ToDoModel()
    {

    }

    public ToDoModel(String task, String date,String priority, int status,String key) {
        this.task = task;
        this.date = date;
        this.priority = priority;
        this.key=key;
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
