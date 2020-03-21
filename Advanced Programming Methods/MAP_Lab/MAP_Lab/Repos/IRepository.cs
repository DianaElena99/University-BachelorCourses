using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Repos
{
    interface IRepository<E, ID>
    {
        void save(E e);
        E delete(ID id);
        E update(E nou);
        E findOne(ID id);
        IEnumerable<E> findAll();
    }
}
