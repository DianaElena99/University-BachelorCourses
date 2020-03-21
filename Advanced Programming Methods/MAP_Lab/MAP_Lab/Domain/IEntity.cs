using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Domain
{
    interface IEntity<ID>
    {
        ID Id { get; set; }
    }
}
