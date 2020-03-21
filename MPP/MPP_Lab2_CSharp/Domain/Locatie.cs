using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Domain
{
    class Locatie
    {
        public int ID { get; set; }
        public string Nume { get; set; }
        public int NrLocuri { get; set; }
        public Locatie(int I, string N, int nr)
        {
            ID = I;
            Nume = N;
            NrLocuri = nr;
        }
    }
}
