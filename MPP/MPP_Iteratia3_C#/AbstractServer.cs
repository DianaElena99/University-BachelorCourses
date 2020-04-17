using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Server
{
    public abstract class AbstractServer
    {
        private TcpListener server;
        private string host;
        private int port;

        public AbstractServer(string host, int port)
        {
            this.host = host;
            this.port = port;
        }

        public void Start()
        {
            IPAddress adr = IPAddress.Parse(host);
            IPEndPoint ep = new IPEndPoint(adr, port);
            server = new TcpListener(ep);
            server.Start();
            while (true)
            {
                TcpClient client = server.AcceptTcpClient();
                processRequest(client);
            }
        }

        public abstract void processRequest(TcpClient client);
    }
}
