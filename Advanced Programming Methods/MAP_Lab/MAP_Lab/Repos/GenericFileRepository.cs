using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MAP_Lab.Domain;
using MAP_Lab.Domain.Validators;

namespace MAP_Lab.Repos
{
    delegate E convertFromString<E>(String s);
    delegate string convertToString<E>(E elem);

    class GenericFileRepository<E,ID> : AbstractRepository<E, ID> where E : class, IEntity<ID>
    {
        private readonly String filename;
        private readonly convertFromString<E> fromString;
        private readonly convertToString<E> toStr;
        public GenericFileRepository(IValidator<E> val, String filename, convertFromString<E> fromStr, 
            convertToString<E> toStr_) : base(val)
        {
            this.filename = filename;
            this.fromString = fromStr;
            this.toStr = toStr_;
            readFromFile();
        }

        private void readFromFile()
        {
            using (TextReader t = File.OpenText(filename))
            {
                String s;
                while ((s = t.ReadLine()) != null)
                {
                    E n = fromString(s);
                    base.save(n);
                }
            }
        }

        private void writeToFile()
        {
            using (var sw = new StreamWriter(filename))
            {
                foreach (E elem in findAll())
                {
                    sw.WriteLine(toStr(elem));
                }
            }
                
        }

        public override void save(E elem)
        {
            base.save(elem);
            writeToFile();
        }

        public override E delete(ID id)
        {
            var deleted = base.delete(id);
            writeToFile();
            return deleted;
        }

        public override E update(E nou)
        {
            var modified = base.update(nou);
            if (modified != null)
            {
                writeToFile();
            }
            return modified;
        }

    }
};
