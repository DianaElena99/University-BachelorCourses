using MAP_Lab.Repos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MAP_Lab.Domain;

namespace MAP_Lab.Service
{
    class ServiceAll
    {
        private PlayerRepo playerRepo;
        private GameRepo gameRepo;
        private ActivePlayerRepo activePlayerRepo;
        private TeamRepo teamRepo;

        public IEnumerable<Game> getAllGames()
        {
            return gameRepo.findAll();
        }

        public ServiceAll(PlayerRepo pr, GameRepo gr, ActivePlayerRepo apr, TeamRepo tr)
        {
            playerRepo = pr;
            gameRepo = gr;
            activePlayerRepo = apr;
            teamRepo = tr;
        }

        // all the players of a given team
        
        public void teamPlayers(string Team)
        { 
            IEnumerable<Player> players = playerRepo.findAll();
            var rs = from p in players
                     where p.Team == Team
                     select p.Name;
            Console.WriteLine("\n The players are : ");
            foreach (var elem in rs)
            {
                Console.WriteLine(elem);
            }
        }

        public IEnumerable<Team> teams()
        {
            return teamRepo.findAll();
        }
            
        // all the active players of a team in a game
        public void activePlayersGameTeam(string Team, int idGame)
        {
            IEnumerable<ActivePlayer> activePlayers = activePlayerRepo.findAll();
            IEnumerable<Player> players = playerRepo.findAll();

            var rs = from p in players
                     join ap in activePlayerRepo.findAll() on p.Id equals ap.IdPlayer
                     join g in gameRepo.findAll() on ap.IdGame equals g.Id
                     join t in teamRepo.findAll() on p.Team equals t.TeamName
                     where p.Team == Team && g.Id == idGame 
                     select new { id=p.Id, nume = p.Name, puncte = ap.NrPoints};

            var rsfinal = rs.GroupBy(p => p.id).Select(e => e.First());

            foreach (var elem in rsfinal)
            {
                Console.WriteLine(elem.id + " Nume : " + elem.nume + "   Puncte : " + elem.puncte );
            }
        }

        // all the games in an interval
        public void allGamesInterval(DateTime start, DateTime end)
        {
            IEnumerable<Game> games = gameRepo.findAll();

            var rs = from g in games
                     where g.Date >= start && g.Date <= end
                     select g;
            foreach (var elem in rs)
            {
                Console.WriteLine(elem.ToString());
            }
        }

        //score of a game
        public void gameScore(int GameId)
        {
             Game gm = gameRepo.findOne(GameId);
             int scorTeam1= 0;
             int scorTeam2 = 0;
             foreach (ActivePlayer ap in activePlayerRepo.findAll())
                 {
                    if (ap.IdGame == GameId && playerRepo.findOne(ap.IdPlayer).Team == gm.Team1)
                       scorTeam1+=ap.NrPoints;
                    else if (ap.IdGame == GameId && playerRepo.findOne(ap.IdPlayer).Team == gm.Team2)
                        scorTeam2 += ap.NrPoints;     
                }

             Console.WriteLine(gm.Team1 + ": " + scorTeam1 + "   " + gm.Team2 + "  :" + scorTeam2);
                     
        }
    };

}
