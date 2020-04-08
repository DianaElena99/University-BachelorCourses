using Common.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.responses
{
    [Serializable]
    public class FilterConcertsResponse : Response
    {
        public List<ConcertDTO> Concerts;
        public FilterConcertsResponse(List<ConcertDTO> C)
        {
            Concerts = C;
        }
    }
}
