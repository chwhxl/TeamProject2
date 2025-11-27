package manage;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public CardLayout cardLayout;
    public JPanel cardPanel; // 화면들이 담길 통
    
    // 다른 파일에서 접근할 수 있게 패널들을 멤버 변수로 둠
    public ShopPanel shopPanel;
    public cartPanel cartPanel;

    public MainFrame() {
        setTitle("와인 쇼핑몰");
        setSize(500, 600); // 크기 설정은 여기서 한 번만!
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. 카드 레이아웃 설정
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 2. 패널(화면) 생성 (this를 넘겨서 서로 소통하게 함)
        shopPanel = new ShopPanel(this);
        cartPanel = new cartPanel();

        // 3. 카드 지갑에 넣기 (이름표 붙임: "SHOP", "CART")
        //cardPanel.add(shopPanel, "SHOP");
        cardPanel.add(cartPanel, "CART");

        add(cardPanel);
        setVisible(true);
    }

    // 화면 바꾸는 함수 (다른 패널들이 이 함수를 호출함)
    public void showCard(String name) {
        // 만약 장바구니로 갈 때는 최신 목록으로 업데이트 해줌
        if(name.equals("CART")) {
            cartPanel.updateCartList(); 
        }
        cardLayout.show(cardPanel, name);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}