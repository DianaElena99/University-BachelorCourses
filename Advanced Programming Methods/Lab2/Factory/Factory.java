package Factory;

import Container.*;

public interface Factory {
    Container createContainer(Strategy strategy);
}
