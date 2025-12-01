package wineshop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        if (wine.getImgPath() != null && !wine.getImgPath().isEmpty()) {
        	ImageIcon icon = new ImageIcon(wine.getImgPath());
        	Image scaledImg = icon.getImage().getScaledInstance(50, 180, Image.SCALE_SMOOTH);
        	imgLabel = new JLabel(new ImageIcon(scaledImg));
        } else {
        	imgLabel = new JLabel("이미지 없음", JLabel.CENTER);
        }
        
        imgLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imgLabel.setPreferredSize(new Dimension(50, 180));
        add(imgLabel, BorderLayout.NORTH);
        
        // 상품명 / 가격 / 도수
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        JLabel name = new JLabel(wine.getName(), JLabel.CENTER);
        JLabel price = new JLabel(wine.getPrice() + "원", JLabel.CENTER);
        JLabel alc = new JLabel(wine.getAlcohol() + "%", JLabel.CENTER);
        
        infoPanel.add(name);
        infoPanel.add(price);
        infoPanel.add(alc);
        
        add(infoPanel, BorderLayout.CENTER);
        
        JButton cartBtn = new JButton("장바구니에 추가");
        cartBtn.setPreferredSize(new Dimension(180, 30));// 높이 고정
        cartBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		CartManage.addCart(wine, 1);
        		JOptionPane.showMessageDialog(null, wine.getName() + "이(가) 장바구니에 추되었습니다.");
        	}
        });
        
        add(cartBtn, BorderLayout.SOUTH);
    }

    public Wine getItem() {
        return wine;
    }
}
