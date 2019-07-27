package Task;

import java.time.*;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    private String taskID;
    private String description;

    public Task(String id, String descr){
        this.taskID  = id;
        this.description = descr;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTaskID(){
        return this.taskID;
    }

    public void setTaskID(String id){
        taskID = id;
    }

    public void setDescription(String descr){
        description = descr;
    }

    public abstract void execute();

    @Override
    public String toString(){
        return " ID : " + this.taskID + " | descr : " + this.description;
    }
}


