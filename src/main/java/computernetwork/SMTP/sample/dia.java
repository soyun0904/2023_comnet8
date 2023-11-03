package computernetwork.SMTP;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class dia extends JFrame{
	public dia() {
	      setLayout(new FlowLayout());
	      JLabel good = new JLabel("메일 전송이 완료되었습니다! :D");
	      good.setSize(200,100);
	      add(good);
	   }
}
