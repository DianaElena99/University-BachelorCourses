using Common.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.requests
{
    [Serializable]
    public class LogoutRequest : Request
    {
        public User user { get; set; }
        public LogoutRequest(User u)
        {
            user = u;
        }
    }
}
