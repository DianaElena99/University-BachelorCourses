using Common.model;
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
    public partial class MainApp : Form
    {
        public ClientCtrl server;
        public User u;
        List<ConcertDTO> concerts;

        public void setUser(User user)
        {
            u = user;
        }

        public void setServer(ClientCtrl ctrl)
        {
            server = ctrl;
        }

        public MainApp(ClientCtrl server)
        {
            this.server = server;
            InitializeComponent();
            concerts = new List<ConcertDTO>();
            try
            {
                concerts = server.getAllConcerts();
                dataGridView1.DataSource = concerts;
                server.updateEvent += userUpdate;
            }
            catch(Exception e)
            {
                MessageBox.Show(e.Message);
            }
        }

        public void userUpdate(object sender, UserEventArgs args)
        {
            if(args.UserEventType == UserEvent.TicketSold)
            {
                dataGridView1.BeginInvoke(new UpdateImplement(this.updateTable), new Object[] { dataGridView1, args.Data});
            }
        }

        private void dateTimePicker1_ValueChanged(object sender, EventArgs e)
        {
            DateTime dt = dateTimePicker1.Value;
            string input = dt.ToShortDateString();
            try
            {
                List<ConcertDTO> lista = server.filterConcerts(input);
                dataGridView2.Visible = true;
                dataGridView2.DataSource = lista;

            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void MainApp_Load(object sender, EventArgs e)
        {
            dataGridView2.Visible = false;
        }


        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                server.logout(u);
                this.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string nume = textBox1.Text;
            int seats = 0;
            if (dataGridView1.SelectedRows.Count == 0)
            {
                MessageBox.Show("La ce concert sa fie biletul?");
                return;
            }
            int idConcert = int.Parse(dataGridView1.SelectedRows[0].Cells[0].Value.ToString());
            int nrLocuriLibere = int.Parse(dataGridView1.SelectedRows[0].Cells[6].Value.ToString());

            try
            {
                seats = int.Parse(textBox2.Text);
                if (seats > nrLocuriLibere)
                {
                    MessageBox.Show("Nu sunt destule locuri");
                    return;
                }
            }
            catch(Exception ex)
            {
                MessageBox.Show("Not a number, sorry not sorry :/ ");
                return;
            }
            if(seats!=0 && nume != "")
            {
                Bilet b = new Bilet(nume, idConcert, seats);
                server.sellTicket(b);
            }
        }

        private void updateTable(DataGridView dg, List<ConcertDTO> nou)
        {
            dg.DataSource = null;
            dg.DataSource = nou;
        }

        public delegate void UpdateImplement(DataGridView dg, List<ConcertDTO> L);


    }
}
