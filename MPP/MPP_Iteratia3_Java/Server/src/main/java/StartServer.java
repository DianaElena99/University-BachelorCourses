import Repository.BiletRepo;
import Repository.ConcertRepo;
import Repository.UserRepo;

public class StartServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {
        UserRepo ur = new UserRepo();
        ConcertRepo cr = new ConcertRepo();
        BiletRepo br = new BiletRepo();

        IService service = new ServiceImplemented(ur, br, cr);
        int serverPort = 55556;
        AbstractServer server = new ObjectConcurrentServer(serverPort, service);
        try{
            server.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
