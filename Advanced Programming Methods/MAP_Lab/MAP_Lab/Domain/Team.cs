using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Domain
{
    class Team : IEntity<int>
    {
        public int Id { get; set; }
        public string TeamName { get; set; }
        public Team(int Id, string TeamName)
        {
            this.Id = Id;
            this.TeamName = TeamName;
        }

        public override string ToString()
        {
            return this.Id + "|" + this.TeamName;
        }

        public override int GetHashCode()
        {
            return Id;
        }

        public override bool Equals(object o)
        {
            if (!(o is Team)) return false;
            var st = (Team)o;
            return st.Id == Id;
        }
    }
}
