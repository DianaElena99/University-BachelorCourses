using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MPP_Lab2_CSharp.Domain;
using System.Data.SQLite;
using log4net;

namespace MPP_Lab2_CSharp.Repositories
{
    class ArtistRepo : IRepo<int, Artist>
    {
                
        public IEnumerable<Artist> findAll()
        {
            return null;
        }

        public Artist findOne(int i)
        {
            throw new NotImplementedException();
        }


        public int size()
        {
            throw new NotImplementedException();
        }

        public void save(Artist e)
        {
            
        }


        public Artist update(Artist nou)
        {
            return null;
        }


        public Artist delete(int i)
        {
            return null;
        }
    }
}
