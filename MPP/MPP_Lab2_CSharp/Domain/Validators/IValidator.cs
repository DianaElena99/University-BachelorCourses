using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MPP_Lab2_CSharp.Domain.Validators
{
    interface IValidator<E>
    {
        void validate(E e);
    }
}
