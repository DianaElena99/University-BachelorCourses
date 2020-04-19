using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Configuration;

namespace SGBD
{
    public partial class Form1 : Form
    {
        private static int IDProf = 1;

        public string connectionStr = ConfigurationManager.AppSettings["connectionString"];
        DataSet ds = new DataSet();

        SqlDataAdapter adapterUni = new SqlDataAdapter();
        SqlDataAdapter adapterProf = new SqlDataAdapter();

        public Form1()
        {
            InitializeComponent();
        }

        public void initIDCounter()
        {
            using(SqlConnection con = new SqlConnection(connectionStr))
            {
                con.Open();
                SqlCommand comanda = new SqlCommand("SELECT max(codprof) FROM PROFESORI", con);
                var R = comanda.ExecuteReader();
                while (R.Read())
                {
                    int N = R.GetInt32(0);
                    IDProf = N;
                }
            }
          
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            

            using (SqlConnection con = new SqlConnection(connectionStr))
            {
                con.Open();
                ds.Clear();

                adapterUni.SelectCommand = new SqlCommand("Select * from Universitate", con);
                adapterUni.Fill(ds, "TabelUniv");

                adapterProf.SelectCommand = new SqlCommand("Select * from Profesori", con);
                adapterProf.Fill(ds, "TabelProfi");
                   
                TabelUniv.DataSource = ds.Tables["TabelUniv"];
                TabelProfi.DataSource = ds.Tables["TabelProfi"];

                TabelUniv.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            }
        }

        //ADAUGARE
        private void button1_Click(object sender, EventArgs e)
        {
            initIDCounter();
            string numePr = textBox1.Text;
            int UnivId = 0;
            try
            {
                UnivId = int.Parse(textBox3.Text);
            }
            catch (Exception ex)
            {
                MessageBox.Show("ID universitate trebuie sa fie numar");
            }
            int nrCursuri = int.Parse(textBox4.Text);
            if (nrCursuri < 1 || nrCursuri > 3)
            {
                MessageBox.Show("Nr invalid de cursuri");
                return;
            }
            string grad = comboBox1.SelectedItem.ToString();
            string specializ = comboBox2.SelectedItem.ToString();

            if (UnivId != 0)
            {
                DialogResult res = MessageBox.Show("Doriti sa adaugati profesorul cu nume : " + numePr +
                " uniID : " + UnivId + " nrCursuri:" + nrCursuri + " specializare:" + specializ + "?", "", MessageBoxButtons.OKCancel);
                if (res == DialogResult.OK)
                {
                    using (SqlConnection con = new SqlConnection(connectionStr))
                    {
                        try
                        {
                            con.Open();
                            IDProf += 2;
                            adapterProf.InsertCommand = new SqlCommand("Insert into Profesori values (@IdProf, @Specializare, @UnivId, @Grad, @NrCursuri, @Nume)", con);
                            adapterProf.InsertCommand.Parameters.AddWithValue("@IdProf", IDProf);
                            adapterProf.InsertCommand.Parameters.AddWithValue("@Nume", numePr);
                            adapterProf.InsertCommand.Parameters.AddWithValue("@Specializare", specializ);
                            adapterProf.InsertCommand.Parameters.AddWithValue("@NrCursuri", nrCursuri);
                            adapterProf.InsertCommand.Parameters.AddWithValue("@Grad", grad);
                            adapterProf.InsertCommand.Parameters.AddWithValue("@UnivId", UnivId);
                            int affectedRows = adapterProf.InsertCommand.ExecuteNonQuery();
                            MessageBox.Show(affectedRows.ToString() + " row(s) affected");
                            ds.Tables["TabelProfi"].Clear();
                            adapterProf.SelectCommand = new SqlCommand("Select * from Profesori", con);
                            adapterProf.Fill(ds, "TabelProfi");
                            TabelProfi.DataSource = ds.Tables["TabelProfi"];
                        }catch(Exception ex)
                        {
                            MessageBox.Show(ex.Message);
                        }
                       
                    }
                }
            }

        }

        //STERGERE - Se sterge profesorul selectat
        // DELETE FROM PROFESORI WHERE codProf = ...

        private void button2_Click(object sender, EventArgs e)
        {
            try { 
            var row = TabelProfi.SelectedRows[0];
            int idProf = int.Parse( TabelProfi.Rows[row.Index].Cells[0].Value.ToString());
            using (SqlConnection con = new SqlConnection(connectionStr))
            {
                con.Open();
                adapterProf = new SqlDataAdapter();
                string comanda = "DELETE FROM PROFESORI WHERE codProf = @idProf";
                adapterProf.DeleteCommand = new SqlCommand(comanda, con);
                adapterProf.DeleteCommand.Parameters.AddWithValue("@idProf", idProf);
                int n = adapterProf.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show(n.ToString() + " row(s) affected");

                ds.Tables["TabelProfi"].Clear();
                adapterProf.SelectCommand = new SqlCommand("SELECT * FROM PROFESORI", con);
                adapterProf.Fill(ds, "TabelProfi");
                TabelProfi.DataSource = ds.Tables["TabelProfi"];
                con.Close();
            }
            }catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        //MODIFICARE
        // UPDATE PROFESORI SET nume = @nume, grad = @grad, 
        //                      nrCursuri = @nrCursuri, specializare = @specializare 
        // WHERE codProf = @codProf  
        private void button3_Click(object sender, EventArgs e)
        {
            try { 
            using (SqlConnection con = new SqlConnection(connectionStr)) {
                con.Open();
                string nume = textBox1.Text;
                string grad = comboBox1.Text;
                string specializare = comboBox2.Text;
                int nrCursuri = int.Parse(textBox4.Text);
                int uniId = int.Parse(textBox3.Text);

                int codProf = int.Parse(TabelProfi.SelectedRows[0].Cells[0].Value.ToString());

                adapterProf = new SqlDataAdapter();

                string cmd = "UPDATE Profesori SET nume = @nume,grad = @grad," +
                    "nrCoursesPerSemester = @nrCursuri, field = @specializare " +
                    "WHERE codProf = @codProf";

                adapterProf.UpdateCommand = new SqlCommand(cmd, con);
                adapterProf.UpdateCommand.Parameters.AddWithValue("@nume", nume);
                adapterProf.UpdateCommand.Parameters.AddWithValue("@nrCursuri", nrCursuri);
                adapterProf.UpdateCommand.Parameters.AddWithValue("@grad", grad);
                adapterProf.UpdateCommand.Parameters.AddWithValue("@specializare", specializare);
                adapterProf.UpdateCommand.Parameters.AddWithValue("@codProf", codProf);

                int n = adapterProf.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show(n.ToString() + " row(s) affected");

                ds.Tables["TabelProfi"].Clear();
                adapterProf.SelectCommand = new SqlCommand("SELECT * FROM PROFESORI", con);
                adapterProf.Fill(ds, "TabelProfi");
                TabelProfi.DataSource = ds.Tables["TabelProfi"];
                con.Close();
            }
            }catch(Exception xe)
            {
                MessageBox.Show(xe.Message);
            }

        }

        private void TabelProfi_SelectionChanged(object s, EventArgs E)
        {
            if (TabelProfi.SelectedRows.Count == 1)
            {
                var idx = TabelProfi.SelectedRows[0];
                int profId = int.Parse(TabelProfi.Rows[idx.Index].Cells[0].Value.ToString());
                textBox1.Text = profId.ToString();

                string nume = TabelProfi.Rows[idx.Index].Cells[5].Value.ToString();
                textBox1.Text = nume;
                comboBox2.Text = TabelProfi.Rows[idx.Index].Cells[1].Value.ToString();
                comboBox1.Text = TabelProfi.Rows[idx.Index].Cells[3].Value.ToString();
                textBox3.Text = TabelProfi.Rows[idx.Index].Cells[2].Value.ToString();
                textBox4.Text = TabelProfi.Rows[idx.Index].Cells[4].Value.ToString();
                
            }
        }

        private void TabelUniv_SelectionChanged(object sender, EventArgs e)
        {


            if (TabelUniv.SelectedRows.Count == 1)
            {
                var idx = TabelUniv.SelectedRows[0];
                int idUniv = int.Parse(TabelUniv.Rows[idx.Index].Cells[0].Value.ToString());
                textBox3.Text = idUniv.ToString();
                using (SqlConnection con = new SqlConnection(connectionStr))
                {
                    try
                    {
                        con.Open();
                        adapterProf = new SqlDataAdapter();

                        string sql = "Select * from profesori where UniID = @IdUniv";
                        SqlCommand comanda = new SqlCommand(sql, con);
                        comanda.Parameters.AddWithValue("@IdUniv", idUniv);

                        adapterProf.SelectCommand = comanda;
                        ds.Tables["TabelProfi"].Clear();
                        adapterProf.SelectCommand = comanda;
                        adapterProf.Fill(ds, "TabelProfi");
                        TabelProfi.DataSource = ds.Tables["TabelProfi"];
                        con.Close();
                    }
                    catch(Exception ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                }
            }
            else
            {
                using (SqlConnection con = new SqlConnection(connectionStr))
                {
                    try {
                    con.Open();
                    adapterProf.SelectCommand = new SqlCommand("Select * from Profesori", con);
                    adapterProf.Fill(ds, "TabelProfi");
                    TabelProfi.DataSource = ds.Tables["TabelProfi"];
                    con.Close();
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                }
            }


        }
    }
}

