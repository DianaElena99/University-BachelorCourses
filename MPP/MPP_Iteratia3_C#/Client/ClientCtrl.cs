using Common.model;
using Common.services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client
{
    public class ClientCtrl : IFestivalObserver
    {
        public event EventHandler<UserEventArgs> updateEvent;
        private readonly IFestivalService server;
        private User currentUser;

        public ClientCtrl(IFestivalService s)
        {
            server = s;
            currentUser = null;
        }

        public void login(User u)
        {
            Console.WriteLine("-- Client Ctrl > Login" + u.username + " " + u.passwd);
            server.Login(u, this);
            Console.WriteLine("--Client ctrl > Sosit de la server raspuns");
            currentUser = u;
            Console.WriteLine("Current user : {0}", u);
        }

        public void logout(User u)
        {
            Console.WriteLine("-- Client Ctrl > Logout " + u.username);
            server.Logout(u, this);
            currentUser = null;
        }

        public void sellTicket(Bilet b)
        {
            Console.WriteLine("-- Client Ctrl > Selling a ticket : " + b.ToString());
            server.sellTicket(b);
            
        }

        protected virtual void OnUserEvent(UserEventArgs args)
        {
            if (updateEvent == null) return;
            updateEvent(this, args);
            Console.WriteLine("Update event called");
        }

        public List<ConcertDTO> getAllConcerts()
        {
            return server.getAllConcerts();
        }

        public List<ConcertDTO> filterConcerts(string data)
        {
            return server.filterConcerts(data);
        }

        public void update(List<ConcertDTO> L)
        {
            UserEventArgs args = new UserEventArgs(UserEvent.TicketSold, L);
            OnUserEvent(args);
        }
    }
}
