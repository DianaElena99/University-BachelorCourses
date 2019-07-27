package Decorator;

import Task.*;
import TaskRunner.*;

public class AbstractTaskRunner implements TaskRunner {
    private TaskRunner tr;
    AbstractTaskRunner(TaskRunner tr_){
        this.tr = tr_;
    }

    @Override
    public void executeOneTask(){
        tr.executeOneTask();
    }

    @Override
    public void executeAll(){
        tr.executeAll();
    }

    @Override
    public void addTask(Task t){
        tr.addTask(t);
    }

    @Override
    public boolean hasTask(){
        return tr.hasTask();
    }
}
