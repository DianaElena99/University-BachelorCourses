using Common.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.responses
{
    [Serializable]
    public class GetAllConcertsResponse : Response
    {
        public List<ConcertDTO> Concerts;
        public GetAllConcertsResponse(List<ConcertDTO> C)
        {
            Concerts = C;
        }
    }
}
