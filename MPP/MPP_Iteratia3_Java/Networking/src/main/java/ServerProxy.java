
import Model.Bilet;
import Model.ConcertDTO;
import Model.User;
import requests.*;
import responses.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerProxy implements IService {

    private String host;
    private int port;
    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponse;
    private volatile boolean finished;

    public ServerProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponse = new LinkedBlockingQueue<>();
    }

    @Override
    public void login(User u, IObserver obs) {
        initializeConnection();
        sendRequest(new LoginRequest(u));
        Response r = readResponse();
        if (r instanceof OkResponse) {
            this.client = obs;
            System.out.println("Proxy --- User autentificat cu succes ");
            return;
        }
        if (r instanceof ErrorResponse) {
            System.out.println("Proxy --- autentificare esuata ");
            closeConnection();
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response readResponse() {
        Response r = null;
        try {
            r = qresponse.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    private void sendRequest(Request Request) {
        try {
            output.writeObject(Request);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    @Override
    public void logout(User u, IObserver obs) throws Exception {
        sendRequest(new LogoutRequest(u));
        Response r = readResponse();
        if(r instanceof  OkResponse){
            System.out.println("Logout cu succes");
        }
        closeConnection();
        if (r instanceof ErrorResponse) {
            throw new Exception("");
        }
    }

    @Override
    public void sellTicket(Bilet b) {
        System.out.println("Trimite cerere cu bilet : " + b);
        sendRequest(new SellTicketRequest(b));
        Response r = readResponse();
        if (r instanceof ErrorResponse) {
            System.out.println(r);
        }
        if(r instanceof OkResponse){
            //TicketSoldResponse rasp = (TicketSoldResponse)r;
            System.out.println("Raspuns la cererea de vandut bilet : ");

        }
    }

    @Override
    public List<ConcertDTO> searchConcerts(LocalDate d) {
        sendRequest(new SearchConcertsRequest(d));
        System.out.println("Sent request to filter concerts by date");
        Response r = readResponse();
        if (r instanceof ErrorResponse) {
            System.out.println(r);
        }
        if(r instanceof SearchConcertsResponse){
            SearchConcertsResponse rsp = (SearchConcertsResponse) r;
            System.out.println("Am primit de la server : " + rsp.concerts);
            return rsp.concerts;
        }
        return null;
    }

    @Override
    public List<ConcertDTO> getAllConcerts() {
        System.out.println(this.getClass() + "Trimite cerere pt toate concertele : ");
        sendRequest(new GetAllConcertsRequest());
        Response rasp = readResponse();
        if (rasp instanceof GetAllConcertsResponse){
            GetAllConcertsResponse r = (GetAllConcertsResponse) rasp;
            return r.getConcerts();
        }
        return null;
    }

/*    public void broadcastTable(List<ConcertDTO> l) throws Exception {
        System.out.println("Broadcasting the table ");
        sendRequest(new UpdateRequest(l));
        Response r = readResponse();
        if (r instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) r;
            throw new Exception(err.toString());
        }
        TicketSoldResponse resp = (TicketSoldResponse) r;
    }
*/
    private class ReaderThread implements Runnable {

        @Override
        public synchronized void run() {
            while (!finished) {
                try {
                    Object resp = input.readObject();
                    System.out.println("responses.Response arrived : " + resp.getClass() + " " + resp.toString());
                    if (resp instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) resp);
                    } else {
                        try {
                            qresponse.put((Response) resp);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleUpdate(UpdateResponse resp) {
        TicketSoldResponse up = (TicketSoldResponse)resp;
        List<ConcertDTO> l = up.actualizat;
        try{
            client.update(l);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
