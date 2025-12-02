package beershop;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import mallmain.Main;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import manage.CartManage;

public class BeerSearchResult extends JPanel{
	private Beer beer;

    public BeerSearchResult(Beer beer) {
        this.beer = beer;

        // 카드 디자인
        setPreferredSize(new Dimension(180, 296));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setLayout(new BorderLayout());

        // 상품 이미지 
        JLabel imgLabel;
        String imgPath = beer.getImgPath();
        
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
        JPanel infoPanel = new JPanel(new BorderLayout(0, 5));
	    JTextPane namePane = new JTextPane();
	
	    namePane.setText(beer.getName());
	    StyledDocument doc = namePane.getStyledDocument();
	    SimpleAttributeSet center = new SimpleAttributeSet();
	    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
	    doc.setParagraphAttributes(0, doc.getLength(), center, false);
	
	    namePane.setEditable(false);
	    namePane.setOpaque(false); // 배경 투명하게
	    namePane.setBorder(null); // 테두리 없애기
	    namePane.setFont(new Font("Noto Sans KR", Font.BOLD, 15)); // 폰트 설정
	    JPanel bottomInfo = new JPanel(new GridLayout(2, 1));
	    bottomInfo.setOpaque(false);
	
	    DecimalFormat df = new DecimalFormat("#,###");
	    JLabel price = new JLabel(df.format(beer.getPrice()) + "원 (재고: " + beer.getStock()+"개)" , JLabel.CENTER);
	    JLabel alc = new JLabel(beer.getAlcohol() + "%", JLabel.CENTER);
		
	    bottomInfo.add(price);
        bottomInfo.add(alc);

        infoPanel.add(namePane, BorderLayout.CENTER);	
	    infoPanel.add(bottomInfo, BorderLayout.SOUTH);
	    add(infoPanel, BorderLayout.CENTER);
	    
        JButton cartBtn = new JButton("장바구니에 추가");
        cartBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));      
        cartBtn.setFocusPainted(false);
        cartBtn.setPreferredSize(new Dimension(180, 30));// 높이 고정
        cartBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		int currentInCart = 0;
        		
        		for (manage.CartProduct cp : CartManage.getCartList()) {
        			if (cp.getProduct().getName().equals(beer.getName())) {
        				currentInCart += cp.getQuantity();
        				break;
        			}
        		}
        		
        		if (currentInCart + 1 > beer.getStock()) {
        	        JOptionPane.showMessageDialog(null, "재고가 부족합니다. (남은 재고: " + beer.getStock() + "개)", "알림", JOptionPane.WARNING_MESSAGE);
        	        return;
        	    }
        		
        		CartManage.addCart(beer, 1);
        		JOptionPane.showMessageDialog(null, beer.getName() + "이(가) 장바구니에 추가되었습니다.");
        	}
        });
        
        add(cartBtn, BorderLayout.SOUTH);
    }

    public Beer getItem() {
        return beer;
    }
}
