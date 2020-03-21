using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MAP_Lab.Domain;
using MAP_Lab.Service;

namespace MAP_Lab.UI
{
    class Consola
    {
        private ServiceAll service;
        public Consola(ServiceAll serv)
        {
            this.service = serv;
        }

        public void Run()
        {
            bool runs = true;
            int cmd = -1;
            while (runs) {
                Console.WriteLine("\t\t MENU \n" +
                    "\t0. EXIT\n" +
                    "\t1. Score of a match\n" +
                    "\t2. Games in an interval\n" +
                    "\t3. Players of a team\n" +
                    "\t4. Active Players of a team in a match\n\n" +
                    ">>> ");
                cmd = int.Parse(Console.ReadLine());

                if (cmd == 0)
                    runs = false;
                if (cmd == 1)
                {
                    foreach(Game g in service.getAllGames())
                    {
                        Console.WriteLine(g);
                    }
                    Console.WriteLine("Select the game");
                    string gid = Console.ReadLine();
                    int gID = int.Parse(gid);
                    service.gameScore(gID);
                }
                if (cmd == 2)
                {
                    string DataStart = "";
                    string DataEnd = "";
                    Console.WriteLine("Start (yyyy-mm-dd) : ");
                    DataStart = Console.ReadLine();
                    Console.WriteLine("End (yyyy-mm-dd) : ");
                    DataEnd = Console.ReadLine();
                    string[] dscomp = DataStart.Split('-');
                    string[] decomp = DataEnd.Split('-');
                    DateTime ds = new DateTime(int.Parse(dscomp[0]), int.Parse(dscomp[1]), int.Parse(dscomp[2]));
                    DateTime de = new DateTime(int.Parse(decomp[0]), int.Parse(decomp[1]), int.Parse(decomp[2]));
                    service.allGamesInterval(ds, de);
                }
                if (cmd == 3)
                {
                    foreach( Team t in service.teams())
                    {
                        Console.WriteLine(t);
                    }
                    Console.WriteLine("\nName of team : ");
                    string nume = Console.ReadLine();
                    service.teamPlayers(nume);
                }
                if (cmd == 4)
                {
                    foreach (Game g in service.getAllGames())
                    {
                        Console.WriteLine(g);
                    }
                    Console.WriteLine("\n\nChoose a game by id :");
                    string gid = Console.ReadLine();
                    //Console.ReadLine();
                    Console.WriteLine("\n Choose a team");
                    string tid = Console.ReadLine();
                    //Console.ReadLine();
                    
                    int gm = int.Parse(gid);
                    service.activePlayersGameTeam(tid,gm);
                }
                if (cmd < 0 || cmd > 4)
                    Console.WriteLine("Invalid");
            }

        }
    }
}
