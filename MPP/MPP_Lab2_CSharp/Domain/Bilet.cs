using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Domain
{
    public class Bilet
    {
        public string IdClient { get; set; }
        public int IdConcert { get; set; }
        public int IdBilet { get; set; }
        public int NrLocuri { get; set; }
        public Bilet(int b, string c, int co, int nr)
        {
            IdBilet = b;
            IdClient = c;
            IdConcert = co;
            NrLocuri = nr;
        }
    }
}
