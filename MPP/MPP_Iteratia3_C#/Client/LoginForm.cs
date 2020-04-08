using Common.model;
using Common.services;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Client
{
    public partial class LoginForm : Form
    {
        public ClientCtrl server;

        public LoginForm(ClientCtrl ctrl)
        {
            InitializeComponent();
            server = ctrl;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string username = textBox1.Text;
            string pass = textBox2.Text;
            User user = new User(username, pass);
            try
            {
                server.login(user);
                MainApp main = new MainApp(server);
                main.setUser(user);
                this.Hide();
                main.Text = "Willkommen zum SpringFestival, " + username;
                main.Show();
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }
    }
}
