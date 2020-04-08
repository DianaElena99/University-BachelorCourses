using MPP_Lab2_CSharp.Domain;
using MPP_Lab2_CSharp.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Service
{
    public class Business
    {
        private ConcertRepo cr;
        private UserRepo ur;
        private BiletRepo br;

        public static int countBilete;

        private List<int> activeUsers;

        public void CumparaBilete(int idConcert, string numeClient, int nrBilete)
        {
            countBilete++;
            int nrLocuriOcup = cr.findOne(idConcert).NrLocuriVandute + nrBilete;
            Bilet b = new Bilet(countBilete, numeClient, idConcert, nrBilete);
            br.saveBilet(b);
        }

        public void UpdateLocuri(int idConcert, int nrLocuriNou)
        {
            Console.WriteLine(nrLocuriNou);
            cr.updateLocuriLibere(idConcert, nrLocuriNou);
        }

        public Business(ConcertRepo c, UserRepo u, BiletRepo b)
        {
            cr = c;
            ur = u;
            br = b;
            countBilete = br.size() ; 
            activeUsers = new List<int>();
        }

        internal List<ConcertDTO> GetConcerte()
        {
            return cr.cautaConcerteDetalii();
        }

        internal int LoginHandler(string user, string pass)
        {
            int rez = ur.CheckLoginCredentials(user, pass);
            if (rez > 0) activeUsers.Add(rez);
            return rez;
        }

        internal List<ConcertDTO> SearchConcerts(DateTime data)
        {
            return cr.cautaConcerteData(data);
        }

        internal void LogoutHandler(int userID)
        {
            this.activeUsers.Remove(userID);
        }
    }
}
