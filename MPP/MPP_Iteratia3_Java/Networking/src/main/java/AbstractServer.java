import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server = null;
    public AbstractServer(int port){
        this.port = port;
    }
    public void start(){
        try{
            server = new ServerSocket(port);
            while(true){
                Socket client = server.accept();
                System.out.println("Client connected...");
                processRequest(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            stop();
        }
    }

    public void stop(){
        try{
            server.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public abstract void processRequest(Socket client);
}
