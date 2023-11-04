package computernetwork.SMTP;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MailClient2 extends JFrame {
	JLabel recipientf = new JLabel("받는 사람");
    JTextField recipientb = new JTextField();
    JLabel subjectf = new JLabel("제목");
    JTextField subjectb = new JTextField();
    JLabel contentf = new JLabel("내용");
    JTextArea contentb = new JTextArea();
    JLabel attachmentLabel = new JLabel("첨부 파일");
    JTextField attachmentPathField = new JTextField();
    JButton browseButton = new JButton("탐색...");

    public MailClient2() {
        setTitle("컴퓨터 네트워크 프로젝트 1 메일 전송");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);

        recipientf.setLocation(20, 20);
        recipientf.setSize(60, 30);
        c.add(recipientf);

        recipientb.setLocation(100, 20);
        recipientb.setSize(350, 30);
        c.add(recipientb);

        subjectf.setLocation(20, 60);
        subjectf.setSize(80, 30);
        c.add(subjectf);

        subjectb.setLocation(100, 60);
        subjectb.setSize(350, 30);
        c.add(subjectb);

        contentf.setLocation(20, 100);
        contentf.setSize(50, 30);
        c.add(contentf);

        contentb.setLocation(100, 100);
        contentb.setSize(450, 150);
        contentb.setLineWrap(true);
        c.add(contentb);

        attachmentLabel.setLocation(20, 260);
        attachmentLabel.setSize(80, 30);
        c.add(attachmentLabel);

        attachmentPathField.setLocation(100, 260);
        attachmentPathField.setSize(350, 30);
        c.add(attachmentPathField);

        browseButton.setLocation(470, 260);
        browseButton.setSize(80, 30);
        browseButton.addActionListener(new ActionListener() {
        	//파일탐색기 처리
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Files", "jpg", "png", "pdf", "txt");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    attachmentPathField.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        c.add(browseButton);

        JButton sendingButton = new JButton("전송");
        sendingButton.addActionListener(new ActionListener() {
        	//버튼이 눌렸을때 이벤트 리스너
            public void actionPerformed(ActionEvent e) {
                try {
                	//받는 사람 칸이 비어있거나 @가 포함되지 않으면 오류 출력
                    if (recipientb.getText().equals("") || recipientb.getText().contains("@") == false) {
                        new dia2("수신자의 성함이 올바르지 않습니다. :(");
                        return;
                    } 
                    //제목 칸이 비어있으면 오류 출력
                    else if (subjectb.getText().equals("")) {
                        new dia2("제목을 적어주십시오. :(");
                        return;
                    }
                    //비정상 입력이 없으면 메일 발송
                    else {
                    	//메일 발송을 처리할 함수로 발는 사람, 제목, 내용, 파일경로 넘겨줌
                        MailServer2.sendMail(recipientb.getText(), subjectb.getText(), contentb.getText(), attachmentPathField.getText());
                        recipientb.setText("");
                        subjectb.setText("");
                        contentb.setText("");
                        attachmentPathField.setText("");
                    }
                } 
                //메일 발송중 예외 발생시 예외처리
                catch (Exception ex) {
                	//예외 스택 트레이스 출력
                    ex.printStackTrace(); 
                    //예외 메시지 출력
                    System.out.println("Error: " + ex.getMessage());  
                    return;
                }
            }
        });
        sendingButton.setLocation(470, 30);
        sendingButton.setSize(80, 50);
        c.add(sendingButton);

        setSize(600, 410);
        setLocation(400, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        MailClient2 mc = new MailClient2();
    }
}
