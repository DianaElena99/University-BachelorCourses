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
    public class ProxyServer : IFestivalService
    {
        private string host;
        private int port;

        private IFestivalObserver client;
        private NetworkStream stream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public ProxyServer(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object resp = formatter.Deserialize(stream);
                    Console.WriteLine("Received response : " + resp);
                    if(resp is UpdateResponse)
                    {
                        handleUpdate((UpdateResponse)resp);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue((Response)resp);
                        }
                        _waitHandle.Set();
                    }
                }
                catch(Exception e)
                {
                    Console.WriteLine(e);
                }
            }
        }

        private void handleUpdate(UpdateResponse resp)
        {
            try
            {
                Console.WriteLine("Handling update");
                TicketSold ts = (TicketSold)resp;
                Console.WriteLine("New list of concerts : \n" + ts.Concerts);
                client.update(ts.Concerts);
            }
            catch(Exception e)
            {
                Console.WriteLine("Exception thrown in ProxyServer -- handle Update");
                Console.WriteLine(e.Message);
            }
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Source);
            }
        }

        private void sendRequest(Request req)
        {
            try
            {
                formatter.Serialize(stream, req);
                stream.Flush();
            }
            catch(Exception e)
            {
                throw new Exception("Error sending object:" + e);
            }
        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                Console.WriteLine("Number of responses in the queue : " + responses.Count);
                _waitHandle.WaitOne();
                lock (responses)
                {
                    response = responses.Dequeue();
                }
            }
            catch(Exception e)
            {
                Console.Write(e.StackTrace + "\n");
            }
            return response;
        }

        public virtual List<ConcertDTO> filterConcerts(string data)
        {
            sendRequest(new FilterConcertsRequest(data));
            List<ConcertDTO> lista = new List<ConcertDTO>();
            Response re = readResponse();
            if (re is ErrorResponse)
            {
                Console.WriteLine(re.ToString());
            }
            FilterConcertsResponse fr = (FilterConcertsResponse)re;
            lista = fr.Concerts;
            return lista;
        }

        public virtual List<ConcertDTO> getAllConcerts()
        {
            sendRequest(new GetAllConcertsRequest());
            Response re = readResponse();
            if (re is ErrorResponse)
            {
                return null;
            }
            List<ConcertDTO> lista = new List<ConcertDTO>();
            GetAllConcertsResponse res = (GetAllConcertsResponse)re;
            lista = res.Concerts;
            return lista;
        }

        public virtual void Login(User u, IFestivalObserver obs)
        {
            initializeConnection();
            Console.WriteLine("In ProxyServer > Login");
            sendRequest(new LoginRequest(u));
            Response resp = readResponse();
            if (resp is OkResponse)
            {
                this.client = obs;
                return;
            }
            if (resp is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)resp;
                closeConnection();
                throw new Exception(err.ToString());
            }
        }

        public virtual void Logout(User u, IFestivalObserver obs)
        {
            sendRequest(new LogoutRequest(u));
            Response res = readResponse();
            closeConnection();
            if (res is ErrorResponse)
            {
                throw new Exception(res.ToString());
            }
        }

        public virtual void sellTicket(Bilet b)
        {
            //List<ConcertDTO> nou = new List<ConcertDTO>();
            Console.WriteLine("Selling a ticket in Proxy Server");
            sendRequest(new SellTicketRequest(b));
            Response re = readResponse();

            if (re is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)re;
                throw new Exception(err.ToString());
            }

            if (re is OkResponse)
            {
                Console.WriteLine("Received and OKResponse --- Ticket was sold successfully");
            }
            //return nou;
        }
    }
}
