using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Domain
{
    class Game : IEntity<int>
    {
        public string Team1 { get; set; }
        public string Team2 { get; set; }
        public DateTime Date { get; set; }
        public int Id { get; set; }
        public Game(int id, string Team1, string Team2, DateTime Date)
        {
            this.Team1 = Team1;
            this.Team2 = Team2;
            this.Id =  id;
            this.Date = Date;
        }

        public override bool Equals(object o)
        {
            if (!(o is Game)) return false;
            var st = (Game)o;
            return st.Id == Id;
        }

        public override int GetHashCode()
        {
            return Id;
        }

        public override string ToString()
        {
            return Id + "|" + Team1 + "|" + Team2 + "|" + Date.ToShortDateString();
        }
    }
}
