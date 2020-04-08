using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.model
{
    [Serializable]
    public class Bilet
    {
        public string Customer { get; set; }
        public int idConcert { get; set; }
        public int seats { get; set; }

        public Bilet(string customer, int idConcert, int seats)
        {
            Customer = customer;
            this.idConcert = idConcert;
            this.seats = seats;
        }

        public override bool Equals(object obj)
        {
            return base.Equals(obj);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public override string ToString()
        {
            return base.ToString();
        }
    }
}
