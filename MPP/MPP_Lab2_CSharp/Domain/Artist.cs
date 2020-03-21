using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Domain
{
    class Artist
    {
        public int ID { get; set; }
        public string Nume { get; set; }

        public Artist(int i, string n)
        {
            ID = i;
            Nume = n;
        }
    }
}
