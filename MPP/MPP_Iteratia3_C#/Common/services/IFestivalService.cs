using Common.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.services
{
    public interface IFestivalService
    {
        void Login(User u, IFestivalObserver obs);
        void Logout(User u, IFestivalObserver obs);
        List<ConcertDTO> getAllConcerts();
        List<ConcertDTO> filterConcerts(string data);
        void sellTicket(Bilet b);
    }
}
