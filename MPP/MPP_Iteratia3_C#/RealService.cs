using Common.model;
using Common.services;
using Server.repos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Server
{
    public class RealService : IFestivalService
    {
        public BiletRepo br { get; set; }
        public ConcertRepo cr { get; set; }
        public UserRepo ur { get; set; }
        public Dictionary<User, IFestivalObserver> loggedUsers;

        public RealService(BiletRepo br, ConcertRepo cr, UserRepo ur)
        {
            this.br = br;
            this.cr = cr;
            this.ur = ur;
            loggedUsers = new Dictionary<User, IFestivalObserver>();
        }

        public List<ConcertDTO> filterConcerts(string data)
        {
            List<ConcertDTO> result = new List<ConcertDTO>();
            try
            {
                result = cr.cautaConcerteData(data);
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return result;
        }

        public List<ConcertDTO> getAllConcerts()
        {
            List<ConcertDTO> result = new List<ConcertDTO>();
            try
            {
                result = cr.cautaConcerteDetalii();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return result;
        }

        public void Login(User u, IFestivalObserver obs)
        {
            Console.WriteLine("Login in Real Service");
            try
            {
                int valid = ur.CheckLoginCredentials(u.username, u.passwd);
                if (valid != -1)
                {
                    loggedUsers.Add(u, obs);
                    Console.WriteLine("User added in loggedUsers");
                }
                else
                {
                    throw new Exception("invalid credentials");
                }
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        public void Logout(User u, IFestivalObserver obs)
        {
            try
            {
                loggedUsers.Remove(u);
            }
            catch(Exception e)
            {
                Console.Write(e.Message);
            }
        }

        public void sellTicket(Bilet b)
        {
            try
            {
                br.saveBilet(b);
                int nrLocuriNou = b.seats + cr.findOne(b.idConcert).NrLocuriVandute;
                cr.updateLocuriLibere(b.idConcert, nrLocuriNou);
                List<ConcertDTO> L = cr.cautaConcerteDetalii();
                notifyAll(L);
                //return L;
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Message);
                //return new List<ConcertDTO>();
            }
        }

        private void notifyAll(List<ConcertDTO> actual)
        {
            
            try
            {
                foreach (IFestivalObserver obs in loggedUsers.Values)
                {
                    Task.Run(() => obs.update(actual));
                }
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Message);
            }
            /* 
                        foreach(var client in this.loggedUsers.Values)
                        {
                            try
                            {
                                Console.WriteLine("Sending update to : " + client);
                                client.update(actual);
                                Console.WriteLine("Done sending update");
                            }
                            catch(Exception e)
                            {
                                Console.WriteLine(e.Message);
                            }
                        }
                        */
        }
    }
}
