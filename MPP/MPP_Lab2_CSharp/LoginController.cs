using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MPP_Lab2_CSharp.Service;

namespace MPP_Lab2_CSharp
{
    public partial class LoginController : Form
    {
        public Business s;
        public bool UserLoggedIn;
        public int uid;

        public void setService(Business b)
        {
            s = b;
        }

        public LoginController()
        {
            UserLoggedIn = false;
            InitializeComponent();
        }

        public void Login_Click(object sender, EventArgs E)
        {
            string user = textBox1.Text;
            string pass = textBox2.Text;
            int rez = s.LoginHandler(user, pass);
            if (rez > 0)
            {
                uid = rez;
                UserLoggedIn = true;
                this.Close();
            }
            else
            {
                UserLoggedIn = false;
                MessageBox.Show("User sau parola gresite");
            }
        }

        private void LoginController_Load(object sender, EventArgs e)
        {
            
        }
    }
}
