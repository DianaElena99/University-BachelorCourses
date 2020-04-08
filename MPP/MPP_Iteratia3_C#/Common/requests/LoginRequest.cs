using Common.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.requests
{
    [Serializable]
    public class LoginRequest : Request
    {
        public User user { get; set; }
        public LoginRequest(User u)
        {
            user = u;
        }
    }
}
