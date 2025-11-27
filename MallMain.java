package Project_02;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MallMain extends JFrame {
    public MallMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mall Main");
        setSize(400, 600); // 모바일 화면 비율과 비슷하게 설정

        // 메인 패널 설정
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 타이틀 라벨
        JLabel titleLabel = new JLabel("원하는 샵을 선택하세요", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(30, 0, 30, 0)); // 위아래 여백
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        // 3행 1열의 격자 레이아웃 (버튼 사이 간격 20px)
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBorder(new EmptyBorder(20, 50, 50, 50)); // 좌우 여백을 줘서 버튼 크기 조절
        buttonPanel.setBackground(Color.WHITE);

        String[] shopNames = {"Wine", "Beer", "Whiskey"};

        for (String name : shopNames) {
            JButton shopBtn = new JButton(name);

            // 버튼 스타일 꾸미기
            shopBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            shopBtn.setBackground(new Color(240, 240, 240)); // 연한 회색 배경
            shopBtn.setFocusPainted(false); // 클릭 시 테두리 깜빡임 제거

            // 버튼 클릭 이벤트 (지금은 콘솔 출력만)
            shopBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(name + " 버튼이 눌렸습니다.");
                    // 나중에 여기에 화면 이동 코드를 넣으면 됩니다.
                    // 예: if (name.equals("ㅇㅇ샵")) { new WineListApp().setVisible(true); }
                }
            });

            buttonPanel.add(shopBtn);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MallMain());
    }
}
