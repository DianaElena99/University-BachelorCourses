using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MAP_Lab.Domain;
using MAP_Lab.Domain.Validators;

namespace MAP_Lab.Repos
{
    class StudentRepo : GenericFileRepository<Student, int>
    {
        public StudentRepo(IValidator<Student> val,
            string filename) : base(val, filename, String2Stud, Stud2String)
        { }

        private static string Stud2String(Student st)
        {
            return $"{st.Id}|{st.Name}|{st.School}";
        }

        private static Student String2Stud(string st)
        {
            var student = st.Split('|');
            return new Student(int.Parse(student[0]), student[1], student[2]);
        }
    }
}
