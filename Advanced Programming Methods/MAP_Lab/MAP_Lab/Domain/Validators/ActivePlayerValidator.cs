using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Domain.Validators
{
    class ActivePlayerValidator : IValidator<ActivePlayer>
    {
        public void validate(ActivePlayer entity)
        {
            int errCount = 0;
            if (entity.IdGame < 0)
                errCount++;
            if (entity.IdPlayer < 0)
                errCount++;
            if (entity.NrPoints < 0)
                errCount++;
            if (errCount != 0)
                throw new ApplicationException("Invalid Active Player");
        }
    }
}
