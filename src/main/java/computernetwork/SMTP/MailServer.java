package computernetwork.SMTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.activation.MimetypesFileTypeMap;

class MailServer {
   private static DataOutputStream dos;

  public static void sendMail(String recipient, String subject, String content) throws Exception
  {  
     
       int delay = 1000;
     //   String user = "computernetwork833@gmail.com";
     //   String pass = "dwqfypqfxbacjuhc"; 
     String user = "comnepro8@gmail.com";
       String pass = "djnwdrblljcvaaog"; 
       
       String username = Base64.getEncoder().encodeToString(user.getBytes());
       String password = Base64.getEncoder().encodeToString(pass.getBytes());
    
       SSLSocket sock = (SSLSocket)((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket("smtp.gmail.com", 465);
       // SSL_socket 생성 :    
       final BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
       // 들어오는 data 버퍼에 들여오고 읽기   
       Thread readingThread = (new Thread(new Runnable()
       {
            public void run()
            {
                 try
                 {
                      String line;
                      while((line = br.readLine()) != null) {    // packet 내용 읽기
                         
                          System.out.println("SERVER: "+line);
                         //if (mailSent)
                         //{
                         //     break;
                         //}
                      }

                      new dia();
                 }
                 catch(IOException e) { // 예외처리
                	 e.printStackTrace();
                	 new dia2("메일 전송 중 오류가 발생하였습니다. :(");
                     System.out.println(e);
                	 return;
                 }
                 catch (Exception e)    // 예외 처리
                 {
                	 new dia2("메일 전송 중 오류가 발생하였습니다. :(");
                      e.printStackTrace();
                      System.out.println(e);
                      return ;
                 }
            }
       }));

     readingThread.start();

     dos = new DataOutputStream(sock.getOutputStream());

       send("HELO smtp.gmail.com\r\n");
       Thread.sleep(delay);
       send("AUTH LOGIN\r\n");
       Thread.sleep(delay);
       send(username + "\r\n");
       Thread.sleep(delay);
       send(password + "\r\n");
       Thread.sleep(delay);
       send("MAIL FROM:<comnepro8@gmail.com>\r\n");
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
       //mailSent=true;
       readingThread.join();

       br.close();
       dos.close();

       sock.close();
  }

    private static void send(String s) throws Exception {
     
      dos.write(s.getBytes("UTF-8"));
       System.out.println("CLIENT: "+s);
  }
}
