package User;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MallMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MallMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mall Main");
        setSize(1600, 900); // 모바일 화면 비율과 비슷하게 설정

        // ★ 프레임 레이아웃 설정
        setLayout(new BorderLayout());

        // --------------------
        // 1. 고정 패널 (NORTH)
        // --------------------
        JPanel fixedPanel = new JPanel();
        fixedPanel.add(new JLabel("고정된 영역"));
        fixedPanel.setPreferredSize(new Dimension(1600, 100));
        fixedPanel.setBackground(Color.LIGHT_GRAY);

        add(fixedPanel, BorderLayout.NORTH);  // 위치 지정 필수

        // --------------------
        // 2. 교체되는 패널 (CENTER, CardLayout)
        // --------------------
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // (1) 메인 패널
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("원하는 샵을 선택하세요", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(30, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBorder(new EmptyBorder(20, 50, 50, 50));
        buttonPanel.setBackground(Color.WHITE);

        String[] shopNames = {"Wine", "Beer", "Whiskey"};

        for (String name : shopNames) {
            JButton shopBtn = new JButton(name);

            shopBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            shopBtn.setBackground(new Color(240, 240, 240));
            shopBtn.setFocusPainted(false);

            shopBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(name + " 버튼이 눌렸습니다.");

                    // 이름에 따라 다른 카드 보여주기
                    if (name.equals("Wine")) {
                        cardLayout.show(cardPanel, "WINE");
                    } else if (name.equals("Beer")) {
                        cardLayout.show(cardPanel, "BEER");
                    } else {
                        // Whiskey 화면 아직 없으면 임시 처리
                        JOptionPane.showMessageDialog(
                            MallMain.this,
                            "Whiskey 화면은 아직 준비 중입니다."
                        );
                    }
                }
            });

            buttonPanel.add(shopBtn);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // (2) 다른 화면들 (이미 만든 JPanel 사용)
        JPanel winePanel = new pay();      // pay는 JPanel
        JPanel beerPanel = new history();  // history도 JPanel

        // 카드 등록 (이름은 마음대로, show에서 동일하게만 사용)
        cardPanel.add(mainPanel, "MAIN");
        cardPanel.add(winePanel, "WINE");
        cardPanel.add(beerPanel, "BEER");

        // cardPanel을 CENTER에 배치
        add(cardPanel, BorderLayout.CENTER);

        // 처음은 메인 화면
        cardLayout.show(cardPanel, "MAIN");

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MallMain());
    }
}
