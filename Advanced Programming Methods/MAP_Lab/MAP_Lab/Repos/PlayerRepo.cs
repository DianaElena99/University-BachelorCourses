using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MAP_Lab.Domain;
using MAP_Lab.Domain.Validators;


namespace MAP_Lab.Repos
{
    class PlayerRepo : GenericFileRepository<Player, int>
    {
        public PlayerRepo(IValidator<Player> val,
            string filename) : base(val, filename, String2Player, Player2String)
        { }
        
        private static string Player2String(Player p)
        {
            return $"{p.Id}|{p.Name}|{p.School}|{p.Team}";
        }    

        private static Player String2Player(string p)
        {
            var player = p.Split('|');
            return new Player(int.Parse(player[0]), player[1],
               player[2], player[3]);
        }
    }
}

