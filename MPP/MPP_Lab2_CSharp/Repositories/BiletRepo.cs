using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MPP_Lab2_CSharp.DBStuff;
using MPP_Lab2_CSharp.Domain;
using log4net;

namespace MPP_Lab2_CSharp.Repositories
{
    public class BiletRepo : IRepoBiletUtils
    {
        public SQLiteConnectionFactory factory;
        public static ILog logg = LogManager.GetLogger("Logger for BiletRepo");
        public BiletRepo()
        {
            logg.Info("\n User repo created \n");
            factory = new SQLiteConnectionFactory();
        }

        public void saveBilet(Bilet b)
        {
            try { 
            IDbConnection con = factory.createConnection();
            con.Open();
            using (var com = con.CreateCommand())
            {
                logg.Info("\n Entered function SaveBilet\n ");

                com.CommandText = "INSERT INTO BILET VALUES (@p1, @p2, @p3, @p4)";
                var param = com.CreateParameter();
                param.ParameterName = "@p1";
                param.Value = b.IdBilet;
                com.Parameters.Add(param);

                var param2 = com.CreateParameter();
                param2.ParameterName = "@p2";
                param2.Value = b.IdClient;
                com.Parameters.Add(param2);

                var param3 = com.CreateParameter();
                param3.ParameterName = "@p3";
                param3.Value = b.IdConcert;
                com.Parameters.Add(param3);

                var param4 = com.CreateParameter();
                param4.ParameterName = "@p4";
                param4.Value = b.NrLocuri;
                com.Parameters.Add(param4);
                
                com.ExecuteNonQuery();
                logg.Info("Exiting function SaveBilet");
            }
            }catch(Exception e)
            {
                MessageBox.Show(e.Message);
            }
        }

        public int size()
        {
            logg.Info("\n Finding the number of tickets sold so far\n");
            IDbConnection CON = factory.createConnection();
            int dim = 0;
            CON.Open();
            using(var com = CON.CreateCommand())
            {
                com.CommandText = "SELECT COUNT(*) FROM BILET";
                dim =  int.Parse(com.ExecuteScalar().ToString());
            }
            return dim;
        }

        public IEnumerable<Bilet> findAll()
        {
            throw new NotImplementedException();
        }

        public Bilet findOne(int i)
        {
            throw new NotImplementedException();
        }

    }
}
