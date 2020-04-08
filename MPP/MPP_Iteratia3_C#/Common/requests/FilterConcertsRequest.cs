using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common.requests
{
    [Serializable]
    public class FilterConcertsRequest : Request
    {
        public string data { get; set; }
        public FilterConcertsRequest(string d)
        {
            data = d;
        }
    }
}
