using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MailProject
{
    public partial class ServerSelect : Form
    {
        public ServerSelect()
        {
            InitializeComponent();

            if (Form1.getMailServer() == mailServer.GMAIL)
                radioButton1.Checked = true;
            else
                radioButton2.Checked = true;
        }

        private void CloseButton_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            if (radioButton1.Checked)
                Form1.setMailServer(mailServer.GMAIL);
        }

        private void radioButton2_CheckedChanged(object sender, EventArgs e)
        {
            if (radioButton2.Checked)
                Form1.setMailServer(mailServer.NAVER);
        }
    }
}
