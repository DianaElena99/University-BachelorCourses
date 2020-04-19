using System;

namespace SGBD
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.TabelUniv = new System.Windows.Forms.DataGridView();
            this.parentTable = new System.Windows.Forms.Label();
            this.childTable = new System.Windows.Forms.Label();
            this.TabelProfi = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.textBox3 = new System.Windows.Forms.TextBox();
            this.textBox4 = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.comboBox2 = new System.Windows.Forms.ComboBox();
            ((System.ComponentModel.ISupportInitialize)(this.TabelUniv)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.TabelProfi)).BeginInit();
            this.SuspendLayout();
            // 
            // TabelUniv
            // 
            this.TabelUniv.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.TabelUniv.Location = new System.Drawing.Point(36, 56);
            this.TabelUniv.Margin = new System.Windows.Forms.Padding(5);
            this.TabelUniv.Name = "TabelUniv";
            this.TabelUniv.RowTemplate.Height = 28;
            this.TabelUniv.Size = new System.Drawing.Size(462, 258);
            this.TabelUniv.TabIndex = 0;
            this.TabelUniv.SelectionChanged += new System.EventHandler(this.TabelUniv_SelectionChanged);
            // 
            // parentTable
            // 
            this.parentTable.AutoSize = true;
            this.parentTable.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.parentTable.Location = new System.Drawing.Point(41, 20);
            this.parentTable.Margin = new System.Windows.Forms.Padding(5, 0, 5, 0);
            this.parentTable.Name = "parentTable";
            this.parentTable.Size = new System.Drawing.Size(143, 31);
            this.parentTable.TabIndex = 1;
            this.parentTable.Text = "Universitati";
            // 
            // childTable
            // 
            this.childTable.AutoSize = true;
            this.childTable.Location = new System.Drawing.Point(612, 9);
            this.childTable.Margin = new System.Windows.Forms.Padding(5, 0, 5, 0);
            this.childTable.Name = "childTable";
            this.childTable.Size = new System.Drawing.Size(116, 31);
            this.childTable.TabIndex = 2;
            this.childTable.Text = "Profesori";
            // 
            // TabelProfi
            // 
            this.TabelProfi.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.TabelProfi.Location = new System.Drawing.Point(558, 56);
            this.TabelProfi.Name = "TabelProfi";
            this.TabelProfi.RowTemplate.Height = 28;
            this.TabelProfi.Size = new System.Drawing.Size(488, 312);
            this.TabelProfi.TabIndex = 3;
            this.TabelProfi.SelectionChanged += new System.EventHandler(this.TabelProfi_SelectionChanged);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(590, 444);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(192, 81);
            this.button1.TabIndex = 4;
            this.button1.Text = "Adauga ";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(821, 418);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(225, 64);
            this.button2.TabIndex = 5;
            this.button2.Text = "Sterge";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(821, 504);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(225, 69);
            this.button3.TabIndex = 6;
            this.button3.Text = "Modifica";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(256, 354);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(262, 38);
            this.textBox1.TabIndex = 7;
            // 
            // textBox3
            // 
            this.textBox3.Location = new System.Drawing.Point(256, 415);
            this.textBox3.Name = "textBox3";
            this.textBox3.Size = new System.Drawing.Size(262, 38);
            this.textBox3.TabIndex = 9;
            // 
            // textBox4
            // 
            this.textBox4.Location = new System.Drawing.Point(256, 469);
            this.textBox4.Name = "textBox4";
            this.textBox4.Size = new System.Drawing.Size(262, 38);
            this.textBox4.TabIndex = 10;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(30, 354);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(220, 31);
            this.label3.TabIndex = 11;
            this.label3.Text = "Nume si prenume";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(99, 418);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(151, 31);
            this.label6.TabIndex = 14;
            this.label6.Text = "Universitate";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(8, 469);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(242, 31);
            this.label5.TabIndex = 15;
            this.label5.Text = "Nr cursuri/semestru";
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Items.AddRange(new object[] {
            "Asistent",
            "Lector",
            "Conferentiar",
            "Profesor"});
            this.comboBox1.Location = new System.Drawing.Point(36, 543);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(203, 39);
            this.comboBox1.TabIndex = 16;
            this.comboBox1.Text = "Grad";
            // 
            // comboBox2
            // 
            this.comboBox2.FormattingEnabled = true;
            this.comboBox2.Items.AddRange(new object[] {
            "ComputerScience",
            "Mathematics",
            "Literature",
            "Philosophy",
            "Physics&Chemistry",
            "Medicine",
            "Engineering",
            "Politics",
            "History"});
            this.comboBox2.Location = new System.Drawing.Point(256, 543);
            this.comboBox2.Name = "comboBox2";
            this.comboBox2.Size = new System.Drawing.Size(262, 39);
            this.comboBox2.TabIndex = 17;
            this.comboBox2.Text = "Specializare";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(14F, 31F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1153, 669);
            this.Controls.Add(this.comboBox2);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.textBox4);
            this.Controls.Add(this.textBox3);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.TabelProfi);
            this.Controls.Add(this.childTable);
            this.Controls.Add(this.parentTable);
            this.Controls.Add(this.TabelUniv);
            this.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(5);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.TabelUniv)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.TabelProfi)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }


        #endregion

        private System.Windows.Forms.DataGridView TabelUniv;
        private System.Windows.Forms.Label parentTable;
        private System.Windows.Forms.Label childTable;
        private System.Windows.Forms.DataGridView TabelProfi;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.TextBox textBox3;
        private System.Windows.Forms.TextBox textBox4;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.ComboBox comboBox2;
    }
}

