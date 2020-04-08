using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MPP_Lab2_CSharp.Domain;

namespace MPP_Lab2_CSharp.Repositories
{
    class LocationRepo : IRepo<int, Locatie>
    {

        public IEnumerable<Locatie> findAll()
        {
            throw new NotImplementedException();
        }

        public Locatie findOne(int i)
        {
            throw new NotImplementedException();
        }

        public int size()
        {
            throw new NotImplementedException();
        }


        public void save(Locatie e)
        {
            return;
        }

        public Locatie update(Locatie nou)
        {
            return null;
        }

        public Locatie delete(int i)
        {
            return null;
        }
    }
}
