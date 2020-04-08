using Common.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.responses
{
    [Serializable]
    public class TicketSold : UpdateResponse
    {
        public List<ConcertDTO> Concerts;
        public TicketSold(List<ConcertDTO> c)
        {
            Concerts = c;
        }
    }
}
