using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MPP_Lab2_CSharp.Domain;
using MPP_Lab2_CSharp.DBStuff;
using System.Data.SQLite;
using log4net;
using System.Data;
using System.Windows.Forms;

namespace MPP_Lab2_CSharp.Repositories
{
    public class UserRepo : IRepo<int, Utilizator>
    {
        public ILog logg = LogManager.GetLogger("Logger ConcertRepo");
        public SQLiteConnectionFactory utile;

        public UserRepo()
        {
            utile = new SQLiteConnectionFactory();
            logg.Debug("User repository created");
        }

        public int CheckLoginCredentials(string user, string pass)
        {
            logg.Debug("Verificare credentiale");
            int rez = -1;
            try
            {
                IDbConnection con = utile.createConnection();
                
                using (IDbCommand cmd = con.CreateCommand())
                {
                    con.Open();
                    cmd.CommandText = "SELECT ID FROM USER WHERE NUME = @p1 and PAROLA = @p2";
                    var par1 = cmd.CreateParameter();
                    par1.Value = user;
                    par1.ParameterName = "@p1";
                    cmd.Parameters.Add(par1);

                    var par2 = cmd.CreateParameter();
                    par2.Value = pass;
                    par2.ParameterName = "@p2";
                    cmd.Parameters.Add(par2);

                    int aux = (int) cmd.ExecuteScalar();
                    if (aux > 0)
                    {
                        rez = aux;
                        Console.Write("USER REPO - Utilizator gasit\n");
                    }
                    else
                    {
                        Console.WriteLine("USER REPO - Autentificare nereusita\n");
                    }
                }
            }
            catch(Exception e)
            {
                MessageBox.Show(e.Message);
            }
            return rez;

        }

        public Utilizator update(Utilizator nou)
        {
            return null;
        }

        public Utilizator delete(int i)
        {
            return null;
        }

        public void save(Utilizator e)
        {
            return;
        }


        public IEnumerable<Utilizator> findAll()
        {
            throw new NotImplementedException();
        }

        public Utilizator findOne(int i)
        {
            throw new NotImplementedException();
        }

        public int size()
        {
            throw new NotImplementedException();
        }

    }
}
