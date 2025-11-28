package mallmain;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class ShopPanel extends JPanel {
    public ShopPanel(Main mainFrame, String shopName, Color bgColor) {
    	
    	setLayout(new BorderLayout());
    	setBackground(bgColor);

        // 상단: 상점 이름
        JLabel titleLabel = new JLabel(shopName + "에 오신 것을 환영합니다", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 중앙: 상점 내용 (상태 유지가 잘 되는지 테스트하는 곳)
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false); // 투명하게 해서 배경색 보이게
        contentPanel.setLayout(new FlowLayout());

        contentPanel.add(new JLabel("장바구니 메모: "));
        JTextField memoField = new JTextField(15); // 여기에 글을 쓰고 뒤로가기를 눌러보세요.
        contentPanel.add(memoField);
        
        JCheckBox checkBox = new JCheckBox("쿠폰 적용하기");
        checkBox.setOpaque(false);
        contentPanel.add(checkBox);

        add(contentPanel, BorderLayout.CENTER);

        // 하단: 뒤로가기 버튼
        JButton btnBack = new JButton("홈으로 돌아가기");
        btnBack.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        btnBack.setPreferredSize(new Dimension(100, 50));
        
        // 클릭하면 다시 HOME 화면을 보여달라고 요청
        btnBack.addActionListener(e -> mainFrame.showMainCard("HOME"));
        
        add(btnBack, BorderLayout.SOUTH);
    }
}
