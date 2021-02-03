
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*----------JButton을 상속한 Alphabet 클래스---------------*/

class Alphabet extends JButton {
	// ***멤버변수***
	private int alphabet;	// 알파벳의 유니코드 정수값
	
	// ***생성자***
	public Alphabet(int Alphabet) {	
		// 멤버변수 초기화
		this.alphabet = Alphabet;
		// 객체속성 설정
		setSize(50, 50);	
		setBackground(Color.LIGHT_GRAY);
		setText("?");	// 정확한 순서에 클릭되기 전에는 '?'를 표시
	}
	
	// ***메소드***
	public int getValue() {
		return alphabet;	// 알파벳 유니코드 정수값 반환
	}
	
	public void showAlphabet() {
		setText(Character.toString((char)alphabet));	// 정확한 순서의 Alphabet 클릭 시 호출할 함수
	}
}



/*----------Main함수 포함하는 AlphabetFrame 클래스---------------*/

public class AlphabetFrame extends JFrame {
	// ***멤버변수***
	private Alphabet[] Data = new Alphabet[26];	// Alphabet 객체의 배열
	private JLabel Text = new JLabel("");	// User에게 표시할 메세지 창
	private int count = 0;	// 현재 클릭해야 하는 Alphabet의 순서 저장
	private int record = 0;	// 클릭한 횟수를 저장해 종료 시 출력
	
	// ***생성자***
	public AlphabetFrame() {
		setTitle("Java Assignment 1");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 800);
		setLayout(null);
		
		// *프레임 상단 제목*
		JLabel title = new JLabel("Click Alphabet in Order!");
		title.setSize(300, 80);
		title.setLocation(150, 20);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Calibri", Font.BOLD, 30));
		add(title);
		
		// *Alphabet 객체들을 추가할 JPanel*
		JPanel p = new JPanel();
		p.setSize(500, 500);
		p.setLocation(50, 200);
		p.setLayout(null);
		p.setBackground(Color.WHITE);
		add(p);
		
		// *멤버변수 Data 객체 생성 및 추가*
		int x, y;
		boolean flag;
		for(int i = 0; i < 26; i++) {
			Data[i] = new Alphabet((int)'A' + i);	// 'A'~'Z'의 유니코드 값은 연속적임을 이용
			/* 
			 * <위치설정 난수 x, y>
			 * x, y를 50간격으로 발생시키고
			 * 반복문을 사용하여 이전 객체들과 생성할 객체가 겹쳐서 출력되지 않도록 함 
			 */
			while(true) {
				x = 50 * (int)(Math.random()*9);
				y = 50 * (int)(Math.random()*9);
				
				flag = true;	// flag를 true로 초기화
				
				for(int j = 0; j < i; j++) {	// i번째 객체 생성중이므로, 생성된 0 부터 i-1까지 비교
					if(Data[j].getX() == x && Data[j].getY() == y)  {
						flag = false;	// 생성할 객체 좌표가 생성된 객체와 겹치면 false
						break;
					}
					else
						flag = true;
				}
				
				if(flag==true)
					break;	// 위 for문 실행 후에도 flag가 true라면  (x, y)는 중복없음
			}
			
			Data[i].setLocation(x, y);
			Data[i].addActionListener(new MyActionListener());
			p.add(Data[i]);
		}
		
		// *멤버변수 Text 객체 속성 설정 및 추가*
		Text.setSize(500, 80);
		Text.setLocation(50, 100);
		Text.setOpaque(true);
		Text.setBackground(Color.PINK);
		Text.setHorizontalAlignment(JLabel.CENTER);
		Text.setFont(new Font("Calibri", Font.BOLD, 20));
		add(Text);
		
		setVisible(true);
		
	}
	
	// ***Alphabet객체에 추가할 ActionListener***
	class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			record++;	// 클릭 시 기록 증가
			Alphabet a = (Alphabet)e.getSource();	// 클릭된 객체 a
			
			// *마지막 버튼을 클릭한 경우*
			if(count + (int)'A' == (int)'Z') {
				// Alphabet이 붙어있는 패널의 객체들을 삭제하고 이를 반영하여 출력
				JPanel p = (JPanel)a.getParent();
				p.removeAll();
				repaint();
				
				Text.setText("Finish!   >> Record : " + record + " Clicks");	// User에게 몇 번 클릭했는지 기록을 표시
			}
			
			// *정확한 순서의 Alphabet을 클릭한 경우*
			else if(a.getValue() == count + (int)'A') {
				// Alphabet의 저장 알파벳 보여주고, 색을 회색으로 변경, 비활성화
				a.showAlphabet();
				a.setBackground(Color.DARK_GRAY);
				a.setEnabled(false);
				// 정확한 답을 맞췄음을 User에게 출력 후, 순서증가
				Text.setText("Correct!");
				count++;
			}
			
			// *순서가 아닌 Alphabet을 클릭한 경우*
			else {
				String print = "Next Alphabet >> ";	// User에게 보여줄 문장 저장할 String
				int nextX, nextY;
				nextX = a.getX() - Data[count].getX();	//클릭한 Alphabet의 X좌표 - 원래 순서의 X좌표
				nextY = a.getY() - Data[count].getY();	//클릭한 Alphabet의 Y좌표 - 원래 순서의 Y좌표
				
				// Math.abs()로 좌표 차의 절댓값을 쓰고 방향은 Left, Right, Up, Down 등 Text로 직접 전달 
				if(nextX >= 0)
					print += "Move! Left : " + Math.abs(nextX) + ", ";
				else
					print += "Move! Right : " + Math.abs(nextX) + ", ";
				
				if(nextY >= 0)
					print += "Up : " + Math.abs(nextY);
				else
					print += "Down : " + Math.abs(nextY);
				
				// 조건에 맞게 이어붙인 print를 User에게 출력
				Text.setText(print);
			}
			
		}
	
	}
	
	public static void main(String args[]) {
		new AlphabetFrame();
	}
}
