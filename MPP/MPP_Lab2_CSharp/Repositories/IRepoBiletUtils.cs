using MPP_Lab2_CSharp.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Repositories
{
    interface IRepoBiletUtils : IRepo<int, Bilet>
    {
        void saveBilet(Bilet b);
    }
}
