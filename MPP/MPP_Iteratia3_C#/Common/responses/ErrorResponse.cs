using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.responses
{
    [Serializable]
    public class ErrorResponse : Response
    {
        string mesg;
        public ErrorResponse(string m)
        {
            mesg = m;
        }
    }
}
