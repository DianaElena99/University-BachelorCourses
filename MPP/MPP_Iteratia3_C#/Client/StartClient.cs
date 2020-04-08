using Common;
using Common.services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Client
{
    static class StartClient
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            IFestivalService serv = new ProxyServer("127.0.0.1", 1234);
            ClientCtrl ctrl = new ClientCtrl(serv);
            LoginForm form = new LoginForm(ctrl);
            Application.EnableVisualStyles();
            //Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(form);
        }
    }
}
