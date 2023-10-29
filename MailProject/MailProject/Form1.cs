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

        private TcpClient Client;
        //private NetworkStream Stream;
        //private StreamReader Reader;
        //private StreamWriter Writer;

        private String SmtpServer = "smtp.gmail.com";
        private int SMTPPort = 465;
        public Form1()
        {
            InitializeComponent();

            Client = new TcpClient(SmtpServer, SMTPPort);
            //Stream = Client.GetStream();
            //Reader = new StreamReader(Stream);
            //Writer = new StreamWriter(Stream);
        }

        public static void setMailServer(mailServer a)
        {
            s = a;
        }
        public static mailServer getMailServer()
        {
            return s;
        }

        private void ConnectServer()
        {
            if (Client.Connected)
            {
                Client.Close();
                //SSLStream.Close();
                //Stream.Close();
                //Reader.Close();
                //Writer.Close();
            }

            switch (s)
            {
                case mailServer.GMAIL:
                    SmtpServer = "smtp.gmail.com";
                    SMTPPort = 587;
                    break;
                case mailServer.NAVER:
                    SmtpServer = "smtp.naver.com";
                    SMTPPort = 587;
                    break;
            }
            Client.Connect(SmtpServer, SMTPPort);
            //SSLStream = new SslStream(Client.GetStream());
            //SSLStream.AuthenticateAsClient(SmtpServer);

            //Stream = Client.GetStream();
            //Reader = new StreamReader(Stream);
            //Writer = new StreamWriter(Stream);
        }
        private void sendMessage(string message)
        {
            // SSLStream.Write(Encoding.UTF8.GetBytes(message));
            //SSLStream.Flush();

            //Writer.WriteLine(message);
            //Writer.Flush();
            //return Reader.ReadLine();
        }
        private String returnMessage(StreamReader reader)
        {
            string str = string.Empty;
            string strTemp = string.Empty;
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
                str += strTemp;
            }

            return str;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                //ConnectServer();

                byte[] buffer;
                string str = string.Empty;
                string strTemp;


                System.Net.Security.SslStream sslstream = new SslStream(Client.GetStream());
                Thread.Sleep(500);

                sslstream.AuthenticateAsClient(SmtpServer);
                Thread.Sleep(500);

                StreamWriter sw = new StreamWriter(sslstream);
                StreamReader reader = new StreamReader(sslstream);
                Thread.Sleep(500);

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
                    str += strTemp;
                }


                MessageBox.Show("메일 전송에 성공했습니다.", "전송 성공");
            }
            catch (Exception ex)
            {
                MessageBox.Show("메일 전송에 실패했습니다.", "전송 실패");
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            ServerSelect s = new ServerSelect();
            s.ShowDialog();
        }
    }
}