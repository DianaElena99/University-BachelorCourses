using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MAP_Lab.Domain;

namespace MAP_Lab.Domain.Validators
{
    class StudentValidator : IValidator<Student>
    {

        string[] Schools = new string[8] { "Sc.gimn Horea",
            "Sc Octavian Goga",
            "Lic.Teor Lucian Blaga",
            "Lic.Teor Onisifor Ghibu",
            "CN Emil Racovita",
            "Lic.Teor Bathory Istvan",
            "Lic.Teor ELF",
            "Lic.Teor Liviu Rebreanu" };

        public void validate(Student st)
        {
            string err = "";
            if (st.Id < 0)
                err += "Id-ul trebuie sa fie pozitiv";
            if (st.Name == "")
                err += "Nume invalid";
            if (st.School == "")
                err += "Nume scoala vid";
            if (!Schools.Contains(st.School))
                err += "Nume scoala invalid";
            if (err.Length > 1)
                throw new ApplicationException("Student invalid");
        }
    }
}
