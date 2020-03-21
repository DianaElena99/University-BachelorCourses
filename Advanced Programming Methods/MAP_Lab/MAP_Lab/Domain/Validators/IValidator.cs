using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Domain.Validators
{
    interface IValidator<E>
    {
        void validate(E entity);
    }
}
