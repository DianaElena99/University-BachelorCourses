using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Server
{
    public abstract class AbstractConcurrentServer : AbstractServer
    {
        public AbstractConcurrentServer(string host, int port) : base(host, port)
        {
        }

        public override void processRequest(TcpClient client)
        {
            Thread t = createWorker(client);
            t.Start();
        }

        public abstract Thread createWorker(TcpClient client);
    }
}
