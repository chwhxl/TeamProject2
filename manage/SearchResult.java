// 논의 필요

package proj2;

import javax.swing.*;
import java.awt.*;

public class SearchResult extends JPanel {

    private Item item;

    public SearchResult(Item item) {
        this.item = item;

        // 카드 디자인
        setLayout(new GridLayout(4, 1));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(150, 120));

        // 상품명 / 가격 / 도수
        JLabel name = new JLabel(item.getName(), JLabel.CENTER);
        JLabel price = new JLabel(item.getPrice() + "원", JLabel.CENTER);
        JLabel alc = new JLabel(item.getAlcohol() + "%", JLabel.CENTER);

        // 상품 이미지 (임시)
        JLabel img = new JLabel("(이미지)", JLabel.CENTER);

        add(img);
        add(name);
        add(price);
        add(alc);
    }

    public Item getItem() {
        return item;
    }
}
