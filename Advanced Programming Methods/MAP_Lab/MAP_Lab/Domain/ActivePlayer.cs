using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Domain
{
    class ActivePlayer : IEntity<Tuple<int,int>>
    {
        public Tuple<int,int> Id { get; set; }
        public int IdPlayer { get; set; }
        public int IdGame { get; set; }
        public int NrPoints { get; set; }
        public string Type { get; set; }

        public ActivePlayer(int IdP, int IdG, int NrP, int Type_)
        {
            this.IdGame = IdG;
            this.IdPlayer = IdP;
            this.Id = new Tuple<int,int>(IdP, IdG);
            this.NrPoints = NrP;
            if (Type_ == 0)
                this.Type = "REZERVA";
            else
                this.Type = "PARTICIPANT";
        }

        public override string ToString()
        {
            return IdPlayer + "|" + IdGame + "|" + NrPoints + "|" + Type;
        }

        public override bool Equals(object obj)
        {
            if (!(obj is ActivePlayer)) return false;
            var st = (ActivePlayer)obj;
            return st.Id == Id;
        }
        public override int GetHashCode()
        {
            return Id.GetHashCode() ;
        }
    }
}
