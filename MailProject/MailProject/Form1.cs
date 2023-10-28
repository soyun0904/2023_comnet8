using System.Net;
using System.Net.Mail;
using System.Net.Sockets;
using System.Text;
using System.Windows.Forms;

namespace MailProject
{
    public enum mailServer { GMAIL, NAVER };

    public partial class Form1 : Form
    {
        static mailServer s = mailServer.GMAIL;

        private TcpClient Client;
        private NetworkStream Stream;
        private StreamReader Reader;
        private StreamWriter Writer;

        private String SmtpServer;
        private int SMTPPort;
        public Form1()
        {
            InitializeComponent();

            Client = new TcpClient("smtp.gmail.com", 587);
            Stream = Client.GetStream();
            Reader = new StreamReader(Stream);
            Writer = new StreamWriter(Stream);
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
                Stream.Close();
                Reader.Close();
                Writer.Close();
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
            Client = new TcpClient(SmtpServer, SMTPPort);
            Stream = Client.GetStream();
            Reader = new StreamReader(Stream);
            Writer = new StreamWriter(Stream);
        }
        private String sendMessage(string message)
        {
            Writer.WriteLine(message);
            Writer.Flush();
            return Reader.ReadLine();
        }


        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                ConnectServer();

                byte[] buffer;
                String str;



                str = "HELO " + SmtpServer + "\r\n";
                Console.Text = sendMessage(str);
                //buffer = Encoding.ASCII.GetBytes(str);
                //Stream.Write(buffer, 0, buffer.Length);

                //str = "EHLO " + SmtpServer + "\r\n";
                //Console.Text = sendMessage(str);

                str = "AUTH LOGIN\r\n";
                Console.Text = sendMessage(str);

                str = "Y29tbmVwcm84QGdtYWlsLmNvbQ==\r\n";
                //Writer.WriteLine(Convert.ToBase64String(Encoding.UTF8.GetBytes(str)));
                //Writer.Flush();
                //Console.Text =  Reader.ReadLine();

                Console.Text = sendMessage(str);

                str = "ZGpud2RyYmxsamN2YWFvZw==\r\n";
                Console.Text = sendMessage(str);
                //Writer.WriteLine(Convert.ToBase64String(Encoding.UTF8.GetBytes(str)));
                //Writer.Flush();
                //Console.Text = Reader.ReadLine();

                str = "MAIL FROM: " + "<" + "comnepro8@gmail.com" + ">\r\n";
                Console.Text = sendMessage(str);

                str = "RCPT TO: " + "<" + Mail_Receiver.Text + ">\r\n";
                Console.Text = sendMessage(str);

                str = "DATA: " + "\r\n";
                Console.Text = sendMessage(str);

                str = "SUBJECT: " + Mail_Subject.Text + "\r\n" + Mail_Text.Text + "\r\n" + "." + "\r\n";
                Console.Text = sendMessage(str);

                str = "QUIT\r\n";
                Console.Text = sendMessage(str);


                //SmtpClient smtp = new SmtpClient("smtp.gmail.com", 587);
                //smtp.EnableSsl = true; // SSL 사용
                //smtp.DeliveryMethod = SmtpDeliveryMethod.Network;

                // 아웃룩, Live 또는 Hotmail의 계정과 암호를 지정
                //smtp.Credentials = new NetworkCredential("limesarudsa@gmail.com", "qgvq einr nsdo qxoj");

                //MailMessage msg = new MailMessage("limesaru@gmail.com", Mail_Receiver.Text, Mail_Subject.Text, Mail_Text.Text);
                //msg.IsBodyHtml = true;
                //msg.SubjectEncoding = Encoding.UTF8;
                //msg.BodyEncoding = Encoding.UTF8;

                //smtp.Send(msg);
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