
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*----------JButton�� ����� Alphabet Ŭ����---------------*/

class Alphabet extends JButton {
	// ***�������***
	private int alphabet;	// ���ĺ��� �����ڵ� ������
	
	// ***������***
	public Alphabet(int Alphabet) {	
		// ������� �ʱ�ȭ
		this.alphabet = Alphabet;
		// ��ü�Ӽ� ����
		setSize(50, 50);	
		setBackground(Color.LIGHT_GRAY);
		setText("?");	// ��Ȯ�� ������ Ŭ���Ǳ� ������ '?'�� ǥ��
	}
	
	// ***�޼ҵ�***
	public int getValue() {
		return alphabet;	// ���ĺ� �����ڵ� ������ ��ȯ
	}
	
	public void showAlphabet() {
		setText(Character.toString((char)alphabet));	// ��Ȯ�� ������ Alphabet Ŭ�� �� ȣ���� �Լ�
	}
}



/*----------Main�Լ� �����ϴ� AlphabetFrame Ŭ����---------------*/

public class AlphabetFrame extends JFrame {
	// ***�������***
	private Alphabet[] Data = new Alphabet[26];	// Alphabet ��ü�� �迭
	private JLabel Text = new JLabel("");	// User���� ǥ���� �޼��� â
	private int count = 0;	// ���� Ŭ���ؾ� �ϴ� Alphabet�� ���� ����
	private int record = 0;	// Ŭ���� Ƚ���� ������ ���� �� ���
	
	// ***������***
	public AlphabetFrame() {
		setTitle("Java Assignment 1");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 800);
		setLayout(null);
		
		// *������ ��� ����*
		JLabel title = new JLabel("Click Alphabet in Order!");
		title.setSize(300, 80);
		title.setLocation(150, 20);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Calibri", Font.BOLD, 30));
		add(title);
		
		// *Alphabet ��ü���� �߰��� JPanel*
		JPanel p = new JPanel();
		p.setSize(500, 500);
		p.setLocation(50, 200);
		p.setLayout(null);
		p.setBackground(Color.WHITE);
		add(p);
		
		// *������� Data ��ü ���� �� �߰�*
		int x, y;
		boolean flag;
		for(int i = 0; i < 26; i++) {
			Data[i] = new Alphabet((int)'A' + i);	// 'A'~'Z'�� �����ڵ� ���� ���������� �̿�
			/* 
			 * <��ġ���� ���� x, y>
			 * x, y�� 50�������� �߻���Ű��
			 * �ݺ����� ����Ͽ� ���� ��ü��� ������ ��ü�� ���ļ� ��µ��� �ʵ��� �� 
			 */
			while(true) {
				x = 50 * (int)(Math.random()*9);
				y = 50 * (int)(Math.random()*9);
				
				flag = true;	// flag�� true�� �ʱ�ȭ
				
				for(int j = 0; j < i; j++) {	// i��° ��ü �������̹Ƿ�, ������ 0 ���� i-1���� ��
					if(Data[j].getX() == x && Data[j].getY() == y)  {
						flag = false;	// ������ ��ü ��ǥ�� ������ ��ü�� ��ġ�� false
						break;
					}
					else
						flag = true;
				}
				
				if(flag==true)
					break;	// �� for�� ���� �Ŀ��� flag�� true���  (x, y)�� �ߺ�����
			}
			
			Data[i].setLocation(x, y);
			Data[i].addActionListener(new MyActionListener());
			p.add(Data[i]);
		}
		
		// *������� Text ��ü �Ӽ� ���� �� �߰�*
		Text.setSize(500, 80);
		Text.setLocation(50, 100);
		Text.setOpaque(true);
		Text.setBackground(Color.PINK);
		Text.setHorizontalAlignment(JLabel.CENTER);
		Text.setFont(new Font("Calibri", Font.BOLD, 20));
		add(Text);
		
		setVisible(true);
		
	}
	
	// ***Alphabet��ü�� �߰��� ActionListener***
	class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			record++;	// Ŭ�� �� ��� ����
			Alphabet a = (Alphabet)e.getSource();	// Ŭ���� ��ü a
			
			// *������ ��ư�� Ŭ���� ���*
			if(count + (int)'A' == (int)'Z') {
				// Alphabet�� �پ��ִ� �г��� ��ü���� �����ϰ� �̸� �ݿ��Ͽ� ���
				JPanel p = (JPanel)a.getParent();
				p.removeAll();
				repaint();
				
				Text.setText("Finish!   >> Record : " + record + " Clicks");	// User���� �� �� Ŭ���ߴ��� ����� ǥ��
			}
			
			// *��Ȯ�� ������ Alphabet�� Ŭ���� ���*
			else if(a.getValue() == count + (int)'A') {
				// Alphabet�� ���� ���ĺ� �����ְ�, ���� ȸ������ ����, ��Ȱ��ȭ
				a.showAlphabet();
				a.setBackground(Color.DARK_GRAY);
				a.setEnabled(false);
				// ��Ȯ�� ���� �������� User���� ��� ��, ��������
				Text.setText("Correct!");
				count++;
			}
			
			// *������ �ƴ� Alphabet�� Ŭ���� ���*
			else {
				String print = "Next Alphabet >> ";	// User���� ������ ���� ������ String
				int nextX, nextY;
				nextX = a.getX() - Data[count].getX();	//Ŭ���� Alphabet�� X��ǥ - ���� ������ X��ǥ
				nextY = a.getY() - Data[count].getY();	//Ŭ���� Alphabet�� Y��ǥ - ���� ������ Y��ǥ
				
				// Math.abs()�� ��ǥ ���� ������ ���� ������ Left, Right, Up, Down �� Text�� ���� ���� 
				if(nextX >= 0)
					print += "Move! Left : " + Math.abs(nextX) + ", ";
				else
					print += "Move! Right : " + Math.abs(nextX) + ", ";
				
				if(nextY >= 0)
					print += "Up : " + Math.abs(nextY);
				else
					print += "Down : " + Math.abs(nextY);
				
				// ���ǿ� �°� �̾���� print�� User���� ���
				Text.setText(print);
			}
			
		}
	
	}
	
	public static void main(String args[]) {
		new AlphabetFrame();
	}
}
