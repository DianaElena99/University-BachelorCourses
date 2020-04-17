using Common;
using Common.services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Server
{
    class MyConcurrentServer : AbstractConcurrentServer
    {
        private IFestivalService server;
        private ClientWorker worker;

        public MyConcurrentServer(string host, int port, IFestivalService serv) : base(host, port)
        {
            server = serv;
        }

        public override Thread createWorker(TcpClient client)
        {
            worker = new ClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
