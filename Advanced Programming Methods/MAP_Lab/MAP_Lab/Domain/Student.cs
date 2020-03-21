using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Domain
{
    class Student : IEntity<int>
    {
        public int Id { get; set; }
        public string Name{get;set;}
        public string School { get; set; }

        public Student(int Id, string Name, string School)
        {
            this.Id = Id;
            this.Name = Name;
            this.School = School;
        }

        public override string ToString()
        {
            return Id + "|" + Name + "|" + School;
        }

        public override bool Equals(object o)
        {
            if (!(o is Student)) return false;
            var st = (Student)o;
            return st.Id == Id;

        }

        public override int GetHashCode()
        {
            return Id;
        }
    }
}
