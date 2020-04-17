using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Server
{
    using Server;
    using Server.repos;

    public class StartServer
    {
        static void Main(string[] args)
        {
            UserRepo userRepo = new UserRepo();
            ConcertRepo concertRepo = new ConcertRepo();
            BiletRepo biletRepo = new BiletRepo();

            RealService service = new RealService(biletRepo, concertRepo, userRepo);
            MyConcurrentServer server = new MyConcurrentServer("127.0.0.1", 1234, service);
            Console.Write("Concurrent Server created ...  \n ... ");
            server.Start();
            Console.Write(" started ... ");
            Console.ReadLine();
        }
    }
}
