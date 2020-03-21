using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MAP_Lab.Domain;
using MAP_Lab.Domain.Validators;

namespace MAP_Lab.Repos
{
    abstract class AbstractRepository<E, ID> : IRepository<E, ID> where E:IEntity<ID>
    {
        private IDictionary<ID, E> elems;
        private IValidator<E> validator;

        public AbstractRepository(IValidator<E> val)
        {
            this.validator = val;
            this.elems = new Dictionary<ID, E>();
        }

        public virtual E delete(ID id)
        {
            try
            {
                E target = elems[id];
                elems.Remove(id);
                return target;
            }
            catch(Exception e)
            {
                Console.WriteLine("ID invalid");
                return default(E);
            }
        }

        public IEnumerable<E> findAll()
        {
            return elems.Values;
        }
        public E findOne(ID id)
        {
            return elems[id];
        }
        public virtual void save(E e)
        {
            validator.validate(e);
            if (elems.ContainsKey(e.Id))
                return;
            elems.Add(e.Id, e);
        }
        public virtual E update(E nou)
        {
            var found = findOne(nou.Id);
            if (found != null)
            {
                elems[nou.Id] = nou;
                return found;
            }
            elems[nou.Id] = nou;
            return default(E);
        }
    }
}
