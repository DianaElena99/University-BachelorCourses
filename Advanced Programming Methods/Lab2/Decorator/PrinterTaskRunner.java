package Decorator;

import TaskRunner.TaskRunner;

import java.time.LocalDateTime;

public class PrinterTaskRunner extends AbstractTaskRunner{
    public PrinterTaskRunner(TaskRunner tr_){
        super(tr_);
    }

    @Override
    public void executeOneTask(){
        super.executeOneTask();
        System.out.println(LocalDateTime.now());
    }

    @Override
    public void executeAll(){
        while (super.hasTask())
            executeOneTask();
    }
}
