package manage;
import javax.swing.*;
import java.awt.event.*;

public class ShopPanel extends JPanel {
    MainFrame mainFrame; // 메인 프레임에 연락하기 위한 변수

    public ShopPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null); // 자유 배치

        JLabel title = new JLabel("=== 와인 상품 목록 ===");
        title.setBounds(150, 20, 200, 30);
        add(title);

        // 테스트용 와인 담기 버튼
        JButton btnAdd = new JButton("레드 와인 담기 (3만원)");
        btnAdd.setBounds(100, 100, 300, 50);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Cart 클래스의 static 리스트에 추가
                // (Wine 클래스 생성자는 본인 코드에 맞게 수정하세요)
                cart.addtoCart(new Wine("맛있는 레드와인", 30000, null, null, null, null, 13.5, 2023));
                JOptionPane.showMessageDialog(null, "장바구니에 담았습니다!");
            }
        });
        add(btnAdd);

        // 장바구니로 이동 버튼
        JButton btnGoCart = new JButton("장바구니 확인하러 가기 ->");
        btnGoCart.setBounds(100, 400, 300, 50);
        btnGoCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 메인 프레임에게 "CART 화면 보여줘"라고 요청
                mainFrame.showCard("CART");
            }
        });
        add(btnGoCart);
    }
}