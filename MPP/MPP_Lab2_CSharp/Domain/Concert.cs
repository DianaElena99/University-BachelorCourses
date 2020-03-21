using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Domain
{
    public class Concert
    {
        public int IDLoc { get; set; }
        public int IDArtist { get; set; }
        public int IDConcert { get; set; }
        public DateTime data { get; set; }
        public int NrLocuriVandute { get; set; }

        public Concert(int id, int ida, int idl, int nr, DateTime d)
        {
            IDLoc = idl;
            IDArtist = ida;
            IDConcert = id;
            NrLocuriVandute = nr;
            data = d;
        }
    }
}
