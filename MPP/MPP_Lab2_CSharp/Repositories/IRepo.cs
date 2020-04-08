using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Repositories
{
    interface IRepo<ID, E>
    {
        E findOne(ID i);
        IEnumerable<E> findAll();
        int size();
    }
}
