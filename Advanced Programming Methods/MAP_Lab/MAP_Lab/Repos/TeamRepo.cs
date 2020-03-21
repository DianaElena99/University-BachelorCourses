using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MAP_Lab.Domain;
using MAP_Lab.Domain.Validators;
namespace MAP_Lab.Repos
{
    class TeamRepo : GenericFileRepository<Team, int>
    {
        public TeamRepo(IValidator<Team> val, string filename)
            :base(val, filename, Str2Team, Team2Str )
        {

        }

        private static Team Str2Team(string str)
        {
            var team = str.Split('|');
            return new Team(int.Parse(team[0]), team[1]);
        }

        private static string Team2Str(Team t)
        {
            return $"{t.Id}|{t.TeamName}";
        }
    }
}
