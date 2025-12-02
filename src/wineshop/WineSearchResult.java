package wineshop;

import javax.swing.*;

import mallmain.Main;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import manage.CartManage;

public class WineSearchResult extends JPanel{
	private Wine wine;

    public WineSearchResult(Wine wine) {
        this.wine = wine;

        // 카드 디자인
        setPreferredSize(new Dimension(180, 280));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setLayout(new BorderLayout());

        // 상품 이미지 
        JLabel imgLabel;
        String imgPath = wine.getImgPath();
        
        if (imgPath != null && !imgPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(imgPath);
            
        	if (icon.getIconWidth() > 0) {
            	Image scaledImg = icon.getImage().getScaledInstance(-1, 175, Image.SCALE_SMOOTH);
            	imgLabel = new JLabel(new ImageIcon(scaledImg));
            } else {
            	System.out.println("이미지 로드 실패: " + imgPath);
            	imgLabel = new JLabel("X", JLabel.CENTER);
            	imgLabel.setForeground(Color.RED);
            }
        } else {
        	imgLabel = new JLabel("이미지 없음", JLabel.CENTER);
        }
        
        imgLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imgLabel.setPreferredSize(new Dimension(100, 180));
        add(imgLabel, BorderLayout.NORTH);
        
        // 상품명 / 가격 / 도수
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        JLabel name = new JLabel(wine.getName(), JLabel.CENTER);
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedPrice = df.format(wine.getPrice());
        JLabel price = new JLabel(formattedPrice + "원", JLabel.CENTER);
        JLabel alc = new JLabel(wine.getAlcohol() + "%", JLabel.CENTER);
        
        infoPanel.add(name);
        infoPanel.add(price);
        infoPanel.add(alc);
        
        add(infoPanel, BorderLayout.CENTER);
        
        JButton cartBtn = new JButton("장바구니에 추가");
        cartBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));      
        cartBtn.setFocusPainted(false);
        cartBtn.setPreferredSize(new Dimension(180, 30));// 높이 고정
        cartBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		CartManage.addCart(wine, 1);
        		JOptionPane.showMessageDialog(null, wine.getName() + "이(가) 장바구니에 추가되었습니다.");
        	}
        });
        
        add(cartBtn, BorderLayout.SOUTH);
    }

    public Wine getItem() {
        return wine;
    }
}
