
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
import java.util.ArrayList;
import java.util.List;

public class ClientWorker implements Runnable, IObserver{
    private IService service;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientWorker(IService service, Socket socket){
        this.service = service;
        this.socket = socket;

        try{
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try{
                Object req = input.readObject();
                Object resp = handleRequest((Request)req);
                if (resp!=null){
                    sendResponse((Response)resp);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private Object handleRequest(Request req) throws Exception {
        Response response = null;
        if(req instanceof LoginRequest){
            System.out.println(this.getClass()+ " Processing login request");
            LoginRequest lo = (LoginRequest) req;
            User u = lo.user;
            try{
                service.login(u, this);
                return new OkResponse(u.toString());
            }catch (Exception e){
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }

        if (req instanceof LogoutRequest){
            LogoutRequest lo = (LogoutRequest)req;
            User u = lo.u;
            try{
                service.logout(u, this);
                try{
                    sendResponse(new OkResponse("Logout cu succes"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                connected = false;
            }catch (Exception e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if (req instanceof SellTicketRequest){
            System.out.println(this.getClass()+  " Processing sell ticket ");
            SellTicketRequest s = (SellTicketRequest)req;
            Bilet b = s.getBilet();
            try{
                service.sellTicket(b);
                return new OkResponse("TICKET sold");
            }catch (Exception e){
                return new ErrorResponse("Nu-i ok");
            }
        }

        if(req instanceof SearchConcertsRequest){
            SearchConcertsRequest sr = (SearchConcertsRequest)req;
            LocalDate d = sr.getData();
            List<ConcertDTO> rez = new ArrayList<>();
            try{
               rez= service.searchConcerts(d);
            }catch (Exception e){
                return new ErrorResponse(e.getMessage());
            }
            try{
                sendResponse(new SearchConcertsResponse(rez));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(req instanceof GetAllConcertsRequest){
            System.out.println(this.getClass() + " getting all the concerts ... method handleReq");
            GetAllConcertsRequest gc = (GetAllConcertsRequest)req;
            List<ConcertDTO> rez = null;
            try{
                rez = service.getAllConcerts();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                sendResponse(new GetAllConcertsResponse(rez));
            }catch (Exception e){
                e.printStackTrace();
            }
            //return new GetAllConcertsResponse(rez);
        }

        /*if(req instanceof UpdateRequest){
            System.out.println("requests.Request to update all the clients");
            UpdateRequest u = (UpdateRequest) req;
            service.broadcastTable(u.l);
            return new TicketSoldResponse(u.l);
        }*/
        return response;
    }

    @Override
    public void update(List<ConcertDTO> l) {
        try{
            sendResponse(new TicketSoldResponse(l));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendResponse(Response resp) throws IOException {
        System.out.println("Throwing response : " + resp.getClass() + " from" + service.getClass());
        output.writeObject(resp);
        output.flush();
    }

}
