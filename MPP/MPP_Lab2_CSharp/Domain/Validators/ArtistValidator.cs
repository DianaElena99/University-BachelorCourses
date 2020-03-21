using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Domain.Validators
{
    class ArtistValidator : IValidator<Artist>
    {
        public void validate(Artist e)
        {
            string err = "";
            if (e.ID < 0)
                err += "ID invalid";
            if (e.Nume == "")
                err += "Nume invalid";
            if (err != "")
                throw new ApplicationException(err);
        }
    }
}
