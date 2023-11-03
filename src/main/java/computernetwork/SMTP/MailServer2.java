package computernetwork.SMTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Base64;
import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

class MailServer2 {
    private static DataOutputStream dos;
    private static String username;
    private static String password;

    public static void sendMail(String recipient, String subject, String content, String filePath) throws Exception {
        String user = "comnepro8@gmail.com";
        String pass = "djnwdrblljcvaaog"; 

        username = Base64.getEncoder().encodeToString(user.getBytes());
        password = Base64.getEncoder().encodeToString(pass.getBytes());

        SSLSocket sock = (SSLSocket)((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket("smtp.gmail.com", 465);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
        dos = new DataOutputStream(sock.getOutputStream());

        // 첨부 파일이 있는 경우
        if (filePath != null && !filePath.isEmpty()) {
            sendMailWithAttachment(recipient, subject, content, filePath, br, dos);
        } else {
            sendMailWithoutAttachment(recipient, subject, content, br, dos);
        }

        br.close();
        dos.close();
        sock.close();
    }

    private static void sendMailWithAttachment(String recipient, String subject, String content, String filePath, BufferedReader br, DataOutputStream dos) throws Exception {
        String boundary = "DataSeparatorString";

        send("HELO smtp.gmail.com\r\n");
        readResponse(br);
        
        send("AUTH LOGIN\r\n");
        readResponse(br);
        
        send(username + "\r\n");
        readResponse(br);
        
        send(password + "\r\n");
        readResponse(br);
        
        send("MAIL FROM:<comnepro8@gmail.com>\r\n");
        readResponse(br);
        
        send("RCPT TO:<" + recipient + ">\r\n");
        readResponse(br);
        
        send("DATA\r\n");
        readResponse(br);
        
        send("To: " + recipient + "\r\n");
        //readResponse(br);

        send("Subject: " + subject + "\r\n");
        //readResponse(br);

        // Headers
        send("MIME-Version: 1.0\r\n");
        
        send("Content-Type: multipart/mixed; boundary=" + boundary + "\r\n");
        
        // Text
        send("--" + boundary + "\r\n");
        
        send("Content-Type: text/plain; charset=UTF-8\r\n\r\n");
        
        send(content + "\r\n");
    

        // Attachment
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        fis.read(fileBytes);
        fis.close();

        String fileBase64 = Base64.getEncoder().encodeToString(fileBytes);
        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        
        send("--" + boundary + "\r\n");
        send("Content-Type: " + mimeType + "; name=\"" + file.getName() + "\"\r\n");
        send("Content-Transfer-Encoding: base64\r\n");
        send("Content-Disposition: attachment; filename=\"" + file.getName() + "\"\r\n\r\n");
        send(fileBase64 + "\r\n");

        send("--" + boundary + "--\r\n");
        
        send(".\r\n");
        readResponse(br);
        
        send("QUIT\r\n");
        readResponse(br);
    }

    private static void sendMailWithoutAttachment(String recipient, String subject, String content, BufferedReader br, DataOutputStream dos) throws Exception {
        send("HELO smtp.gmail.com\r\n");
        readResponse(br);
        
        send("AUTH LOGIN\r\n");
        readResponse(br);
        
        send(username + "\r\n");
        readResponse(br);
        
        send(password + "\r\n");
        readResponse(br);
        
        send("MAIL FROM:<comnepro8@gmail.com>\r\n");
        readResponse(br);
        
        send("RCPT TO:<" + recipient + ">\r\n");
        readResponse(br);
        
        send("DATA\r\n");
        readResponse(br);
        
        send("To: " + recipient + "\r\n");
        //readResponse(br);

        send("Subject: " + subject + "\r\n");
        //readResponse(br);

        send(content + "\r\n");
        //readResponse(br);

        send(".\r\n");
        readResponse(br);
        
        send("QUIT\r\n");
        readResponse(br);
    }

    private static void send(String s) throws Exception {
        dos.write(s.getBytes("UTF-8"));
        dos.flush();
        System.out.println("CLIENT: "+s);
    }

    private static void readResponse(BufferedReader br) throws Exception {
        String line = br.readLine();
        System.out.println("SERVER: " + line);
    }
}
