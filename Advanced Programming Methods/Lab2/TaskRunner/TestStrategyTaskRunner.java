package TaskRunner;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Factory.Strategy;
import Task.*;

public class TestStrategyTaskRunner {

    public void main(String[] args){
        ArrayList<MessageTask> messages = new ArrayList<MessageTask>();

        messages.add(new MessageTask("1", "grep", "sed", "awk", "shell", LocalDateTime.now()));
        messages.add(new MessageTask("2", "shell", "regex", "fork", "pipe", LocalDateTime.now()));
        messages.add(new MessageTask("3", "shm", "fifo", "posix", "lpthread", LocalDateTime.now()));
        Strategy str = Strategy.valueOf(args[0]);
        StrategyTaskRunner run = new StrategyTaskRunner(str);

        for (Task t: messages){
            run.addTask(t);
        }

        run.executeAll();
    }
}
