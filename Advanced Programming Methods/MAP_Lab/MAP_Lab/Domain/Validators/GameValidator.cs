using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MAP_Lab.Domain.Validators
{
    class GameValidator : IValidator<Game>
    {
        string[] Teams = new string[5] { "Los Angeles Lackers", "New Orleans Pelicans", "MiamiHeat",
            "Detroit Pistons", "Dallas Mavericks" };

        public void validate(Game entity)
        {
            int errCount = 0;
            
            if (entity.Id < 0)
            {
                Console.WriteLine("Id Invalid");
                errCount++;
            }
            if (!(Teams.Contains(entity.Team1)))
            {
                Console.WriteLine("Echipa 1");
                errCount++;
            }
            if (!(Teams.Contains(entity.Team2)))
            {
                Console.WriteLine("Echipa 2");
                errCount++;
            }
            if (errCount > 0)
                throw new ApplicationException("Invalid Game");
        }
    }
}
