using Common.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.requests
{
    [Serializable]
    public class SellTicketRequest : Request
    {
        public Bilet bilet;
        public SellTicketRequest(Bilet b)
        {
            bilet = b;
        }
    }
}
