using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MPP_Lab2_CSharp.Domain;
using MPP_Lab2_CSharp.Service;

namespace MPP_Lab2_CSharp
{
    public partial class AppController : Form
    {
        public AppController()
        {
            InitializeComponent();
            dataGridView2.Visible = false;
        }


        private void Search_Click(object sender, EventArgs e)
        {
            DateTime data = dateTimePicker1.Value;
            List<ConcertDTO> rez = b.SearchConcerts(data);
            dataGridView2.DataSource = rez;
            dataGridView2.Visible = true;
        }

        

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void Sell_Click(object sender, EventArgs e)
        {
            try
            {
                string nume = textBox1.Text;
                int idConcert = int.Parse(dataGridView1.SelectedRows[0].Cells[0].Value.ToString());
                int nrLocuri = int.Parse(textBox2.Text);
                int nrLocuriLibere = int.Parse(dataGridView1.SelectedRows[0].Cells[6].Value.ToString());
                if (nrLocuriLibere < nrLocuri)
                {
                    MessageBox.Show("Locuri insuficiente");
                }
                else
                {
                    int nrLocuriNou = int.Parse(dataGridView1.SelectedRows[0].Cells[5].Value.ToString()) + nrLocuri;
                    b.CumparaBilete(idConcert, nume, nrLocuri);
                    b.UpdateLocuri(idConcert, nrLocuriNou);

                    dataGridView1.DataSource = b.GetConcerte();
                }               
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        Business b;
        int userID;

        internal void SetService(Business s, int u)
        {
            b = s;
            userID = u;
            List<ConcertDTO> concerte = b.GetConcerte();
            dataGridView1.DataSource = concerte;
            //ColorListener();
        }

        public void ColorListener(object sender, DataGridViewRowPrePaintEventArgs a)
        {
            foreach(DataGridViewRow dr in dataGridView1.Rows)
            {
                if (int.Parse(dr.Cells[6].Value.ToString()) == 0)
                {
                    dr.DefaultCellStyle.ForeColor = Color.Red;
                }
            }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
            b.LogoutHandler(userID);
            this.Close();
        }
    }
}
