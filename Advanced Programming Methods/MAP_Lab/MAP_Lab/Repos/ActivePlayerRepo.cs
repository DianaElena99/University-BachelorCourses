using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MAP_Lab.Domain;
using MAP_Lab.Domain.Validators;

namespace MAP_Lab.Repos
{
    class ActivePlayerRepo : GenericFileRepository<ActivePlayer, Tuple<int,int>>
    {
        public ActivePlayerRepo(IValidator<ActivePlayer> val, string filename) :
            base(val, filename, Str2ActivePlayer, ActivePlayer2Str)
        { }

        private static string ActivePlayer2Str(ActivePlayer ap)
        {
            return $"{ap.IdPlayer}|{ap.IdGame}|{ap.NrPoints}|{ap.Type}";
        }

        private static ActivePlayer Str2ActivePlayer(string str)
        {
            var ap = str.Split('|');
            if (ap[3] == "REZERVA")
                return new ActivePlayer(int.Parse(ap[0]), int.Parse(ap[1]),
                    int.Parse(ap[2]), 0);
            return new ActivePlayer(int.Parse(ap[0]), int.Parse(ap[1]),
                    int.Parse(ap[2]), 1);
        }
    }
}
