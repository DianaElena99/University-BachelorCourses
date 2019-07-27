package Decorator;

import TaskRunner.TaskRunner;

public class DelayTaskRunner extends AbstractTaskRunner {
    public DelayTaskRunner(TaskRunner tr_){
        super(tr_);
    }

    @Override
    public void executeOneTask(){
        try{
            Thread.sleep(3000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        super.executeOneTask();
    }

    @Override
    public void executeAll(){
        while (super.hasTask()){
            try{
                Thread.sleep(3000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            executeOneTask();
        }
    }
}
