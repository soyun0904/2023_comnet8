using System.Net;
using System.Net.Http;
using System.Net.Mail;
using System.Net.Security;
using System.Net.Sockets;
using System.Threading;
using System.Text;
using System.Windows.Forms;

namespace MailProject
{
    public enum mailServer { GMAIL, NAVER };

    public partial class Form1 : Form
    {
        static mailServer s = mailServer.GMAIL;

        private String SmtpServer = "smtp.gmail.com";
        private int SMTPPort = 465;

        public Form1()
        {
            InitializeComponent();
        }

        public static void setMailServer(mailServer a)
        {
            s = a;
        }
        public static mailServer getMailServer()
        {
            return s;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            TcpClient Client = new TcpClient(SmtpServer, SMTPPort);
            Thread.Sleep(500);

            System.Net.Security.SslStream sslstream = new SslStream(Client.GetStream());
            Thread.Sleep(500);

            sslstream.AuthenticateAsClient(SmtpServer);
            Thread.Sleep(500);

            StreamWriter sw = new StreamWriter(sslstream);
            StreamReader reader = new StreamReader(sslstream);
            Thread.Sleep(500);

            try
            {
                string str;
                string strTemp;

                // refer POP rfc command, there very few around 6-9 command
                sw.WriteLine("EHLO " + "smtp.gmail.com");
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine("AUTH LOGIN");
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine(Convert.ToBase64String(Encoding.UTF8.GetBytes("comnepro8@gmail.com")));
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine(Convert.ToBase64String(Encoding.UTF8.GetBytes("djnwdrblljcvaaog")));
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine("MAIL FROM:<" + "comnepro8@gmail.com" + ">");
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine("RCPT TO:<" + Mail_Receiver.Text + ">");
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine("DATA");
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine("Subject: " + Mail_Subject.Text);
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine(Mail_Text.Text);
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine(".");
                sw.Flush();
                Thread.Sleep(500);

                sw.WriteLine("QUIT");
                sw.Flush();
                Thread.Sleep(500);

                str = string.Empty;
                strTemp = string.Empty;
                while ((strTemp = reader.ReadLine()) != null)
                {
                    // find the . character in line
                    if (strTemp == ".")
                    {
                        break;
                    }
                    if (strTemp.IndexOf("-ERR") != -1)
                    {
                        break;
                    }
                    str += strTemp + "\n";
                }

                MessageBox.Show("메일 전송에 성공했습니다.", "전송 성공");
            }
            catch (Exception ex)
            {
                MessageBox.Show("메일 전송에 실패했습니다.", "전송 실패");
            }

            Client.Close();
            sslstream.Close();
            sw.Close();
            reader.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            ServerSelect s = new ServerSelect();
            s.ShowDialog();
        }
    }
}