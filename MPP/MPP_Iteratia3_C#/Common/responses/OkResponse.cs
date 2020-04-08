using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.responses
{
    [Serializable]
    public class OkResponse : Response
    {
        string mesg;
        public OkResponse(string m)
        {
            mesg = m;
        }
    }
}
