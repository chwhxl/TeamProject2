package MallMain;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import proj2.DetailPanel;
import proj2.Search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MallMain extends JFrame {
    public MallMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 시 프로그램 종료
        setTitle("Mall Main"); // 창 제목 설정
        setSize(1600, 900); // MainFrame 크기 1600 x 900 고정

        // 메인 패널 설정
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 타이틀 라벨
        JLabel titleLabel = new JLabel("Select shop", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBorder(new EmptyBorder(30, 0, 30, 0)); // 위아래 여백
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        // 3행 1열의 격자 레이아웃
        buttonPanel.setLayout(new GridLayout(1, 3, 30, 0));
        buttonPanel.setBorder(new EmptyBorder(200, 200, 200, 200)); // 좌우 여백을 줘서 버튼 크기 조절
        buttonPanel.setBackground(Color.WHITE);

        String[] shopNames = {"Wine", "Beer", "Whiskey"};

        for (String name : shopNames) {
            // 버튼 객체 생성
            JButton shopBtn = new JButton(name);

            // 이미지 아이콘 설정
            String imgPath = "src/img/" + name + ".png";
            ImageIcon icon = new ImageIcon(imgPath);
            Image image = icon.getImage();

            if (image.getWidth(null) != -1) {
                Image scaledImg = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                shopBtn.setIcon(new ImageIcon(scaledImg));
            } else {
                System.err.println("Couldn't find image for " + imgPath);
            }

            // 아이콘과 텍스트 위치, 간격 조절
            shopBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
            shopBtn.setHorizontalTextPosition(SwingConstants.CENTER);
            shopBtn.setIconTextGap(40);

            // 버튼 스타일 꾸미기
            shopBtn.setFont(new Font("Arial", Font.BOLD, 30)); // 글꼴 설정
            shopBtn.setBackground(new Color(240, 240, 240)); // 연한 회색 배경
            shopBtn.setFocusPainted(false); // 클릭 시 테두리 깜빡임 제거
            shopBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

            // 버튼 클릭 이벤트
            shopBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(name + " 버튼이 눌렸습니다.");
                    if (name.equals("Wine")) {
                    	Search searchPanel = new Search(mainPanel);
                        mainPanel.removeAll();
                        mainPanel.add(searchPanel);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                    if (name.equals("Beer")) {
                        System.out.println("Beer shop is under construction.");
                    }
                    if (name.equals("Whiskey")) {
                        System.out.println("Whiskey shop is under construction.");
                    }
                }
            });

            buttonPanel.add(shopBtn);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        MallMain frame = new MallMain();
        frame.setVisible(true);
    }
}
