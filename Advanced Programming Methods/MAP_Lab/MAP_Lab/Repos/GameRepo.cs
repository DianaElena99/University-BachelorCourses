using MAP_Lab.Domain;
using MAP_Lab.Domain.Validators;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Repos
{
    class GameRepo : GenericFileRepository<Game, int>
    {
        public GameRepo(IValidator<Game> val, string filename):
            base(val, filename, Str2Game, Game2Str)
        { }

        public static Game Str2Game(string str)
        {
            var game = str.Split('|');
            var comps = game[3].Split('-');
            DateTime data = new DateTime(int.Parse(comps[0]), int.Parse(comps[1]), int.Parse(comps[2]));
            return new Game(int.Parse(game[0]), game[1], game[2], data);
        }

        public static string Game2Str(Game game)
        {
            return $"{game.Id}|{game.Team1}|{game.Team2}|{game.Date.ToShortDateString()}";
        }
    }
}
