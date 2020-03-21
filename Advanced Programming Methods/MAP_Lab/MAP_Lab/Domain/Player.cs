using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Domain
{
    class Player : Student 
    {
        public string Team { get; set; }
        public Player(int Id, string Name, string School, string Team) : base(Id, Name, School)
        {
            this.Team = Team;
        }

        public override string ToString()
        {
            return this.Id + "|" + this.Name + "|" + this.School + "|" + this.Team;
        }

        public override int GetHashCode()
        {
            return Id;
        }

        public override bool Equals(object o)
        {
            if (!(o is Player)) return false;
            var st = (Player)o;
            return st.Id == Id;
        }
    }


}
