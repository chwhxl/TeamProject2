package mallmain;
 
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;

public class HomePanel extends JPanel{
	public HomePanel(Main mainFrame) {
        // HomePanel 레이아웃 및 배경색 설정
		setLayout(new BorderLayout()); 
        setBackground(Color.WHITE);
        
        // 타이틀 라벨
        JLabel titleLabel = new JLabel("Select shop", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 30, 0));
        buttonPanel.setBorder(new EmptyBorder(200, 200, 200, 200));
        buttonPanel.setBackground(Color.WHITE);
        
        String[] shopNames = {"Wine", "Beer", "Whiskey"};
        
        for (String name : shopNames) {
            // 버튼 객체 생성
            JButton shopBtn = new JButton(name);

            // 버튼 이미지 설정
            String imgPath = "data/mallmain_icon/" + name + ".png";
            ImageIcon icon = new ImageIcon(imgPath);
            Image image = icon.getImage();
            
            // 버튼 이미지 크기 조절
            if (image.getWidth(null) != -1) {
                Image scaledImg = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                shopBtn.setIcon(new ImageIcon(scaledImg));
            } else {
                System.err.println("Couldn't find image for " + imgPath);
            }
            
            // 이미지와 텍스트 위치, 간격 조절
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
					if (name.equals("Wine")) {
						mainFrame.showMainCard("WINE");
					} else if (name.equals("Beer")) {
						mainFrame.showMainCard("BEER");
					} else if (name.equals("Whiskey")) {
						mainFrame.showMainCard("WHISKEY");
					}
				}
			});
			
			buttonPanel.add(shopBtn);
        }
        
        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
	}
}
