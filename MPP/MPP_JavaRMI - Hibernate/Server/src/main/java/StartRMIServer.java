import Model.ConcertDTO;
import Services.IService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repo.BiletRepo;
import repo.ConcertRepo;
import repo.UserRepo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

public class StartRMIServer {
    public static void main(String[] args) {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-rmi-server.xml");
        System.out.println("Started RMI Spring server");
    }
}
