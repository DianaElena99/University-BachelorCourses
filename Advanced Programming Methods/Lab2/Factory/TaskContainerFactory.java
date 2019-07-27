package Factory;

import Container.*;

public class TaskContainerFactory implements Factory{
    @Override
    public Container createContainer(Strategy strategy){
        if (strategy == Strategy.LIFO) {
            return new StackContainer();
        }
        else if(strategy == Strategy.FIFO){
            return new QueueContainer();
        }
        return null;
    }

    private TaskContainerFactory(){}

    private static TaskContainerFactory instance;

    public static TaskContainerFactory getInstance(){
        return instance;
    }
}
