using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SQLite;
using System.Configuration;

namespace Server.repos
{
    public class SQLiteConnectionFactory : ConnectionFactory
    {
        public override IDbConnection createConnection()
        {
            string connectionString = ConfigurationManager.AppSettings.Get("dbURL");

            Console.WriteLine("SQLite ---Se deschide o conexiune la  ... {0}", connectionString);
            return new SQLiteConnection(connectionString);

        }
    }
}
