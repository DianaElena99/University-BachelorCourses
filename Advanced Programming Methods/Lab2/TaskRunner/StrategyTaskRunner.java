package TaskRunner;
import Task.*;
import Container.*;
import Factory.*;

public class StrategyTaskRunner implements TaskRunner{
    private Container cont;
    public StrategyTaskRunner(Strategy str){
        cont = TaskContainerFactory.getInstance().createContainer(str);
    }

    @Override
    public void executeOneTask(){
        if (!cont.isEmpty()){
            cont.remove().execute();
        }
    }

    @Override
    public void executeAll(){
        while (!cont.isEmpty()){
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t){
        cont.add(t);
    }

    @Override
    public boolean hasTask(){
        return !cont.isEmpty();
    }
}
