package computernetwork.SMTP;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MailClient extends JFrame{
   JLabel recipientf = new JLabel("받는 사람");
   JTextField recipientb= new JTextField();
   JLabel subjectf = new JLabel("제목");
   JTextField subjectb = new JTextField();
   JLabel contentf = new JLabel("내용");
   JTextField contentb = new JTextField();
   
   public MailClient() {
      setTitle("컴퓨터 네트워크 프로젝트 1 메일 전송");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Container c= getContentPane();
      c.setLayout(null);
      
      
      recipientf.setLocation(20,20);
      recipientf.setSize(50,30);
      c.add(recipientf);
      
      recipientb.setLocation(80,20);
      recipientb.setSize(320,30);
      c.add(recipientb);
      
      
      subjectf.setLocation(20,60);
      subjectf.setSize(80,30);
      c.add(subjectf);
      
      subjectb.setLocation(80,60);
      subjectb.setSize(320,30);
      c.add(subjectb);
      
      
      contentf.setLocation(20,100);
      contentf.setSize(50,30);
      c.add(contentf);
      
      contentb.setLocation(80,100);
      contentb.setSize(450,250);
      c.add(contentb);
      
      JButton sendingbutton = new JButton("전송");
      sendingbutton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            
            try{
               MailServer.sendMail(recipientb.getText(),subjectb.getText(),contentb.getText());
               recipientb.setText("");
               subjectb.setText("");
               contentb.setText("");
               }
            
            catch(Exception ex) {
               System.out.println("Something is wrong");
               return;
            }
         }
      });
      sendingbutton.setLocation(430,30);
      sendingbutton.setSize(100,50);
      c.add(sendingbutton);
      
      setSize(600,450);
      setLocation(400, 300);
      setVisible(true);
            
   }
}
