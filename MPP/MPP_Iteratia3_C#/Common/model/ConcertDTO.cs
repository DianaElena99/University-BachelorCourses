using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.model
{
    [Serializable]
    public class ConcertDTO
    {
        public int IDConcert { get; set; }
        public DateTime data { get; set; }
        public string artist { get; set; }
        public string locatie { get; set; }
        public int nrLocuriTot { get; set; }
        public int nrLocuriOcup { get; set; }
        public int nrLocuriFree { get; set; }

        public ConcertDTO(int idc, DateTime data, string a, string loc, int tot, int ocup)
        {
            IDConcert = idc;
            this.data = data;
            artist = a;
            locatie = loc;
            nrLocuriTot = tot;
            nrLocuriOcup = ocup;
            nrLocuriFree = tot - ocup;
        }

        public override string ToString()
        {
            return this.artist + " " + this.locatie + " " + this.data.ToShortDateString() + " " + this.nrLocuriFree;
        }
    }
}
