package computernetwork.SMTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Base64;
import javax.activation.MimetypesFileTypeMap;   //첨부 파일 전송 시 필요한 라이브러리
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

class MailServer2 {
    private static DataOutputStream dos;
    private static String username;
    private static String password;

    public static void sendMail(String recipient, String subject, String content, String filePath) throws Exception {
        try {
        String user = "comnepro8@gmail.com";    //보내는 사람 이메일
        String pass = "djnwdrblljcvaaog";   //보내는 사람 비밀번호

        username = Base64.getEncoder().encodeToString(user.getBytes()); //Auth로그인할때 Base64로 인코딩해서 주고받음
        password = Base64.getEncoder().encodeToString(pass.getBytes());

        SSLSocket sock = (SSLSocket)((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket("smtp.gmail.com", 465); //smtp.gmail.com과 handshaking해서 465포트에 connection함
        //^SSL 은 Secure Socket Layer의 약자로 웹 서버와 웹 브라우저  간의 암호화 통신을 위해 응용계층과 TCP/IP 계층에서 동작하는 프로토콜이다. ISO표준 정식 명칭은 TLS(Transport Layer Security)이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
        dos = new DataOutputStream(sock.getOutputStream());

        // 첨부 파일이 있는 경우
        if (filePath != null && !filePath.isEmpty()) {
            sendMailWithAttachment(recipient, subject, content, filePath, br, dos); //받는 이, 제목, 내용, 첨부 파일의 경로, 버퍼, 스트림
        } else {
            sendMailWithoutAttachment(recipient, subject, content, br, dos);
        }

        br.close(); //버퍼 닫기
        dos.close();    //스트림 닫기
        sock.close();   // 소켓 닫기 - connection 끊기
        
        new dia();    //메일 전송이 완료되었을 경우 "메일 전송이 완료되었습니다! :D 출력
    }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println(e);
            new dia2("메일 전송 중 오류가 발생하였습니다. :(");
        } 
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            new dia2("메일 전송 중 오류가 발생하였습니다. :(");
        }
    }

    private static void sendMailWithAttachment(String recipient, String subject, String content, String filePath, BufferedReader br, DataOutputStream dos) throws Exception {   //첨부파일이 포함된 이메일 전송
        String boundary = "DataSeparatorString";    //첨부 파일 보낼때 필요 - 이메일 본문과 첨부 파일을 구분할때 사용

        send("HELO smtp.gmail.com\r\n");        //SMTP프로토콜에서 사용하는 핸드쉐이크 명령어 'HELO' - client의 도메인 or IP주소 함께 전송, \r\n = 명령어의 끝을 의미, 서버가 명령어를 처리하도록 지시 
        readResponse(br);   //서버의 응답 출력
        
        send("AUTH LOGIN\r\n"); //클라이언트 인증 명령어
        readResponse(br);
        
        send(username + "\r\n");    //Base64로 인코딩된 이메일
        readResponse(br);
        
        send(password + "\r\n");    //Base64로 인코딩된 비밀번호
        readResponse(br);
        
        send("MAIL FROM:<comnepro8@gmail.com>\r\n");    //발신자 이메일
        readResponse(br);
        
        send("RCPT TO:<" + recipient + ">\r\n");    //수신자 이메일
        readResponse(br);
        
        send("DATA\r\n");   //본문 전송 시작을 SMTP서버에 알리는 명령어 : 이 다음 전송되는 모든 것은 '.' 행이 수신될 때까지 이메일의 내용으로 처리
        readResponse(br);
        
        send("To: " + recipient + "\r\n");  //수신자 지정 헤더
        

        send("Subject: " + subject + "\r\n");   //메일 제목 지정 헤더
       
        send("MIME-Version: 1.0\r\n");  //MIME버전 명시
        
        send("Content-Type: multipart/mixed; boundary=" + boundary + "\r\n");   //이메일에 본문 내용과 첨부 파일 내용을 구분하기 위해 이를 알림, boundary : 각 부분을 구분하는 문자열
    
        send("--" + boundary + "\r\n"); //첫번째 경계 문자열
        
        send("Content-Type: text/plain; charset=UTF-8\r\n\r\n");    //본문 내용 형식은 text, 문자 인코딩은 UTF-8 임을 알림
        
        send(content + "\r\n"); //이메일 본문 내용 전송
    
        File file = new File(filePath); //첨부파일의 경로를 바탕으로 파일 객체 생성
        FileInputStream fis = new FileInputStream(file);    //첨부 파일 읽기 위한 'FileInputStream'객체 생성
        byte[] fileBytes = new byte[(int) file.length()];   //파일의 내용을 바이트 배열로 읽어 들이고 파일 입력 스트림 닫음
        fis.read(fileBytes);
        fis.close();

        String fileBase64 = Base64.getEncoder().encodeToString(fileBytes);  //첨부파일 내용을 Base64로 인코딩
        String mimeType = new MimetypesFileTypeMap().getContentType(file);  // 파일의 MIME타입 결정 - 종류가 뭐뭐 있는가? image/jpeg, text/plain, application/pdf 등
        
        send("--" + boundary + "\r\n"); //두 번째 경계 문자열
        send("Content-Type: " + mimeType + "; name=\"" + file.getName() + "\"\r\n");    //첨부파일의 MIME타입과 이름 명시
        send("Content-Transfer-Encoding: base64\r\n");  //첨부파일의 전송 인코딩이 Base64임을 명시
        send("Content-Disposition: attachment; filename=\"" + file.getName() + "\"\r\n\r\n");   //첨부파일 명시 및 파일 이름 제공
        send(fileBase64 + "\r\n");  //Base64로 인코딩된 첨부파일의 내용 전송

        send("--" + boundary + "--\r\n");   //마지막 3번째 경계 문자열 - 메시지의 끝을 나타냄
        
        send(".\r\n");  //본문 내용 전송이 끝났음을 SMTP서버에 알림
        readResponse(br);
        
        send("QUIT\r\n");   //SMTP 세션을 종료하고 연결을 닫겠다는 것을 SMTP 서버에 알림
        readResponse(br);    //QUIT응답을 통해 메일이 정상적으로 전송됐는지 확인
    }

    private static void sendMailWithoutAttachment(String recipient, String subject, String content, BufferedReader br, DataOutputStream dos) throws Exception { //첨부파일이 없을 때 이메일 전송
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
        
        send("Subject: " + subject + "\r\n");
       
        send(content + "\r\n");
        
        send(".\r\n");
        readResponse(br);
        
        send("QUIT\r\n");
        readResponse(br);
    }

    private static void send(String s) throws Exception {
        dos.write(s.getBytes("UTF-8")); // 문자열 's'를 UTF-8인코딩 된 바이트 배열로 변환해서 데이터 출력 스트림'dos'에 씀 이때, dos는 소켓을 통해 데이터 전송
        dos.flush();    //  dos에 있는 모든 데이터를 전송
        System.out.println("CLIENT: "+s);   //전송된 문자열 데이터 출력
    }

    private static void readResponse(BufferedReader br) throws Exception {
        String line = br.readLine();    //버퍼리더를 사용하여 한 줄의 텍스트 데이터(응답)을 읽어옴
        System.out.println("SERVER: " + line);  //서버로부터 받은 응답을 출력
    }

}
