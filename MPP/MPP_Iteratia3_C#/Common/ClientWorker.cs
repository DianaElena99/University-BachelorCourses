using Common.model;
using Common.requests;
using Common.responses;
using Common.services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Common
{
    public class ClientWorker : IFestivalObserver
    {
        private IFestivalService server;
        private TcpClient client;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public ClientWorker(IFestivalService server, TcpClient client)
        {
            this.server = server;
            this.client = client;
            try
            {
                stream = client.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
        }

        public void update(List<ConcertDTO> L)
        {
            try
            {
                sendResponse(new TicketSold(L));
            }
            catch(Exception e)
            {
                Console.WriteLine(e);
            }
        }

        public void run()
        {
            while (connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request)request);
                    if (response != null)
                    {
                        sendResponse((Response)response);
                    }
                }
                catch(Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch(Exception e)
                {
                    Console.Write(e.StackTrace + "\n");
                }
            }

            try
            {
                stream.Close();
                client.Close();
            }
            catch(Exception e)
            {
                Console.WriteLine("Error : " + e);
            }
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("Sending response : " + response);
            try
            {
                lock (stream)
                {
                    formatter.Serialize(stream, response);
                    stream.Flush();
                }
            }
            catch(Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            
        }

        private object handleRequest(Request request)
        {
            Response resp = null;
            if(request is LoginRequest)
            {
                Console.WriteLine("Processing a login request ... ");
                LoginRequest logR = (LoginRequest)request;
                User u = logR.user;
                try
                {
                    Console.WriteLine("-- Sending request...");
                    lock (server)
                    { 
                        server.Login(u, this);
                    }
                    return new OkResponse("Utilizator logat ");
                }
                catch(Exception e)
                {
                    connected = false;
                    return new ErrorResponse("Credentiale invalide");
                }
            }

            if(request is LogoutRequest)
            {
                Console.WriteLine("Processing a logout request ... ");
                LogoutRequest logR = (LogoutRequest)request;
                User u = logR.user;
                try
                {
                    lock (server)
                    {
                        server.Logout(u, this);
                    }
                    connected = false;
                    return new OkResponse("User logged out");
                }
                catch(Exception e)
                {
                    return new ErrorResponse("Ceva nu-i ok");
                }
            }

            if(request is GetAllConcertsRequest)
            {
                Console.WriteLine("Processing request : get all concerts");
                try
                {
                    List<ConcertDTO> c = new List<ConcertDTO>();
                    lock (server)
                    {
                        c = server.getAllConcerts();
                    }
                    GetAllConcertsResponse r = new GetAllConcertsResponse(c);
                    sendResponse(r);
                    //return new GetAllConcertsResponse(c);
                }
                catch(Exception e)
                {
                    return new ErrorResponse("Ceva nu-i ok");
                }
            }

            if(request is FilterConcertsRequest)
            {
                Console.WriteLine("Processing request : filter concerts");
                try
                {
                    FilterConcertsRequest re = (FilterConcertsRequest)request;
                    List<ConcertDTO> c = new List<ConcertDTO>();
                    lock (server)
                    {
                        c = server.filterConcerts(re.data);
                    }
                    FilterConcertsResponse r = new FilterConcertsResponse(c);
                    sendResponse(r);
                    //return new FilterConcertsResponse(c);
                }
                catch(Exception e)
                {
                    return new ErrorResponse("nu-i ok");
                }
            }

            if(request is SellTicketRequest)
            {
                try
                {
                    //List<ConcertDTO> L = new List<ConcertDTO>();
                    SellTicketRequest r = (SellTicketRequest)request;
                    Bilet b = r.bilet;
                    lock (server)
                    {
                        server.sellTicket(b);
                    }
                    return new OkResponse("Vandut bilet");
                }
                catch(Exception e)
                {
                    Console.WriteLine(e);
                    return new ErrorResponse("ticket sold error");
                }
            }
            return resp;
        }
    }
}
