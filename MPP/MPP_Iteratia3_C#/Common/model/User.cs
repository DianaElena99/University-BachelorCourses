using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.model
{
    [Serializable]
    public class User
    {
        public string username { get; set; }
        public string passwd { get; set; }

        public User(string username, string passwd)
        {
            this.username = username;
            this.passwd = passwd;
        }
    }
}
