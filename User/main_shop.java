package User;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class main_shop extends JFrame {
	
	public void switchPanel(JPanel current, JPanel next) {
	    setContentPane(next);
	    revalidate();
	    repaint();
	    pack();
	}

    static Scanner sc = new Scanner(System.in);

    // pay, history는 JFrame이 아니라 JPanel이어야 함!
    static JPanel payPanel = new pay();
    static JPanel hisPanel = new history();

    public main_shop() {
        setTitle("쇼핑몰 메인");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);   // 화면 중앙 배치
        setLayout(new BorderLayout());

        // 간단하게 콘솔에서 어떤 화면을 보여줄지 선택
        System.out.println("0: 결제 화면, 1: 구매내역 화면");
        int k = sc.nextInt();

        switch (k) {
            case 0:
                // 결제 화면
                setContentPane(payPanel);
                break;
            case 1:
                // 구매내역 화면
                setContentPane(hisPanel);
                break;
            default:
                // 기본은 결제 화면
                setContentPane(payPanel);
                break;
        }

        setVisible(true);
    }

    public static void main(String[] args) {

               new main_shop();   // main_shop 하나만 생성해서 사용
    
    }
}
