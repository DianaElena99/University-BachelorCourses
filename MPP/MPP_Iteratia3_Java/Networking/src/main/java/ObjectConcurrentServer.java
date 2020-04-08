import java.net.Socket;

public class ObjectConcurrentServer extends AbstractConcurrentServer{
    private IService server;

    public ObjectConcurrentServer(int port, IService ser) {
        super(port);
        this.server = ser;
        System.out.println("Created an Object Concurrent Server");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientWorker worker = new ClientWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;
    }
}
