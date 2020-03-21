using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Domain.Validators
{
    class UserValidator : IValidator<Utilizator>
    {
        public void validate(Utilizator u)
        {
            string err = "";
            if (u.id < 0)
                err += "id invalid";
            if (u.nume=="" || u.parola == "" || !u.email.Contains("@"))
            {
                err += "Nume/Email/Parola invalide";
            }
            if (err != "")
                throw new ApplicationException(err);
        }
    }
}
