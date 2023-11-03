package computernetwork.SMTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.Base64;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

class MailServer {
   private static DataOutputStream dos;

  public static void sendMail(String recipient, String subject, String content) throws Exception
  {
     
       int delay = 1000;
       String user = "computernetwork833@gmail.com";
       String pass = "dwqfypqfxbacjuhc"; 
       
       String username = Base64.getEncoder().encodeToString(user.getBytes());
       String password = Base64.getEncoder().encodeToString(pass.getBytes());

       SSLSocket sock = (SSLSocket)((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket("smtp.gmail.com", 465);
 
       final BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));

       (new Thread(new Runnable()
       {
            public void run()
            {
                 try
                 {
                      String line;
                      while((line = br.readLine()) != null) {
                         
                          System.out.println("SERVER: "+line);

                      }

                 }
                 catch (Exception e)
                 {
                      e.printStackTrace();
                 }
                 dia a = new dia();
                 a.setSize(200,100);
                 a.setLocation(500, 500);
                 a.setVisible(true);
            }
       })).start();
      dos = new DataOutputStream(sock.getOutputStream());

       send("HELO smtp.gmail.com\r\n");
       Thread.sleep(delay);
       send("AUTH LOGIN\r\n");
       Thread.sleep(delay);
       send(username + "\r\n");
       Thread.sleep(delay);
       send(password + "\r\n");
       Thread.sleep(delay);
       send("MAIL FROM:<computerNetwork833@gmail.com>\r\n");
       Thread.sleep(delay);
       send("RCPT TO:<" + recipient +">\r\n");
       Thread.sleep(delay);
       send("DATA\r\n");
       Thread.sleep(delay);
       send("To: " + recipient + "\r\n");
       Thread.sleep(delay);
       send("Subject: " + subject + "\r\n");
       Thread.sleep(delay);
       send(content + "\r\n");
       Thread.sleep(delay);
       send(".\r\n");
       Thread.sleep(delay);
       send("QUIT\r\n");

       br.close();
       dos.close();

       sock.close();
  }

    private static void send(String s) throws Exception {
     
      dos.write(s.getBytes("UTF-8"));
       System.out.println("CLIENT: "+s);
  }
}
