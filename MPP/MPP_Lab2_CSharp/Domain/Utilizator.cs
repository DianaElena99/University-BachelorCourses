using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Domain
{
    public class Utilizator
    {
        public string tipUser { get; set; }
        public int id { get; set; }
        public string nume { get; set; }
        public string email { get; set; }
        public string parola { get; set; }

        public Utilizator(int id, string n, string e, string p)
        {
            this.id = id;
            nume = n;
            email = e;
            parola = p;
            this.tipUser = "ANGAJAT";
        }

        public Utilizator(int id, string n)
        {
            this.id = id;
            nume = n;
            email = "";
            parola = "";
            this.tipUser = "CLIENT";
        }
    }
}
