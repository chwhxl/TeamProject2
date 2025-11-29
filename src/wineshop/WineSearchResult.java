package wineshop;

import javax.swing.*;
import java.awt.*;

public class WineSearchResult extends JPanel{
	private Wine Wine;

    public WineSearchResult(Wine Wine) {
        this.Wine = Wine;

        // 카드 디자인
        setPreferredSize(new Dimension(180, 280));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setLayout(new BorderLayout());

        // 상품 이미지 (임시)
        JLabel img = new JLabel("(이미지)", JLabel.CENTER);
        img.setBorder(BorderFactory.createLineBorder(Color.RED));
        img.setPreferredSize(new Dimension(80, 180));
        add(img, BorderLayout.NORTH);
        
        // 상품명 / 가격 / 도수
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        JLabel name = new JLabel(Wine.getName(), JLabel.CENTER);
        JLabel price = new JLabel(Wine.getPrice() + "원", JLabel.CENTER);
        JLabel alc = new JLabel(Wine.getAlcohol() + "%", JLabel.CENTER);
        
        infoPanel.add(name);
        infoPanel.add(price);
        infoPanel.add(alc);
        
        add(infoPanel, BorderLayout.CENTER);
        
        JButton cartBtn = new JButton("장바구니에 추가");
        cartBtn.setPreferredSize(new Dimension(180, 30)); // 높이 고정
        add(cartBtn, BorderLayout.SOUTH);
    }

    public Wine getItem() {
        return Wine;
    }
}
