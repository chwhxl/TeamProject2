package wineshop;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import mallmain.Main;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import manage.CartManage;

public class WineSearchResult extends JPanel{
	private Wine wine;

    public WineSearchResult(Wine wine) {
        this.wine = wine;
        
        Color lineColor = new Color(220, 220, 220);
        Color boxColor = new Color(248, 248, 248);

        // 카드 디자인
        setPreferredSize(new Dimension(180, 300));
        setBorder(BorderFactory.createLineBorder(lineColor, 1));
        setLayout(new BorderLayout());
        setBackground(boxColor);

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
        
        imgLabel.setOpaque(true);
        imgLabel.setBackground(Color.WHITE);
        imgLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, lineColor));
        imgLabel.setPreferredSize(new Dimension(100, 180));
        add(imgLabel, BorderLayout.NORTH);
        
        imgLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imgLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new WineDetail(wine).setVisible(true);
			}
		});
        
        JPanel infoPanel = new JPanel(new BorderLayout(0, 5));
        infoPanel.setBackground(boxColor);
	    
        JTextPane namePane = new JTextPane();
	
	    namePane.setText(wine.getName());
	    StyledDocument doc = namePane.getStyledDocument();
	    SimpleAttributeSet center = new SimpleAttributeSet();
	    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
	    doc.setParagraphAttributes(0, doc.getLength(), center, false);
	
	    namePane.setEditable(false);
	    namePane.setOpaque(false); 
	    namePane.setBorder(null); 
	    namePane.setFont(Main.getCustomFont("NotoSansKR-SemiBold.ttf", 15)); 
	    
	    JPanel bottomInfo = new JPanel(new GridLayout(2, 1));
	    bottomInfo.setOpaque(false);
	
	    DecimalFormat df = new DecimalFormat("#,###");
	    JLabel price = new JLabel(df.format(wine.getPrice()) + "원 (재고: " + wine.getStock()+"개)" , JLabel.CENTER);
	    price.setFont(Main.getCustomFont("NotoSansKR-SemiBold.ttf", 12));
	    price.setForeground(new Color(90, 90, 90));
	    
	    JLabel alc = new JLabel(wine.getAlcohol() + "%", JLabel.CENTER);
	    alc.setFont(Main.getCustomFont("NotoSansKR-SemiBold.ttf", 12));
	    alc.setForeground(new Color(90, 90, 90));
	    
	    bottomInfo.add(price);
        bottomInfo.add(alc);

        infoPanel.add(namePane, BorderLayout.CENTER);	
	    infoPanel.add(bottomInfo, BorderLayout.SOUTH);
	    add(infoPanel, BorderLayout.CENTER);
        
        JButton cartBtn = new JButton("장바구니에 추가");
        cartBtn.setBackground(new Color(30, 30, 30));
        cartBtn.setFont(Main.getCustomFont("NotoSansKR-SemiBold.ttf", 13));
        cartBtn.setForeground(Color.WHITE);
        cartBtn.setFocusPainted(false);
        cartBtn.setPreferredSize(new Dimension(180, 30));// 높이 고정
        cartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		int currentInCart = 0;
        		
        		for (manage.CartProduct cp : CartManage.getCartList()) {
        			if (cp.getProduct().getName().equals(wine.getName())) {
        				currentInCart += cp.getQuantity();
        				break;
        			}
        		}
        		
        		if (currentInCart + 1 > wine.getStock()) {
        	        JOptionPane.showMessageDialog(null, "재고가 부족합니다. (남은 재고: " + wine.getStock() + "개)", "알림", JOptionPane.WARNING_MESSAGE);
        	        return;
        	    }
        		
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
