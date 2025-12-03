package wineshop;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import manage.CartManage;
import manage.CartProduct;

public class WineDetail extends JFrame {

    private Wine wine;
    private int quantity = 1; // 기본 주문 수량
    private JLabel quantityLabel;

    public WineDetail(Wine wine) {
        this.wine = wine;

        // 1. 프레임 설정
        setTitle(wine.getName() + " Detail");
        setSize(800, 600); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // 왼쪽 와인 이미지
        JPanel leftPanel = new JPanel(new GridBagLayout()); 
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        leftPanel.setPreferredSize(new Dimension(300, 0)); 

        JLabel imgLabel = new JLabel();
        String imgPath = wine.getImgPath();
        
        if (imgPath != null && !imgPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(imgPath);
            if (icon.getIconWidth() > 0) {
                Image scaled = icon.getImage().getScaledInstance(-1, 350, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(scaled));
            } else {
                imgLabel.setText("이미지 없음");
            }
        } else {
            imgLabel.setText("No Image");
        }
        
        imgLabel.setHorizontalAlignment(JLabel.CENTER); 
        
        leftPanel.add(imgLabel);
        add(leftPanel, BorderLayout.WEST);


        // 오른쪽 상세 정보, 장바구니
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(40, 20, 40, 40)); 

        // 와이너리 정보
        JLabel wineryLabel = new JLabel(wine.getWinery());
        wineryLabel.setFont(new Font("Noto Sans KR", Font.PLAIN, 16));
        wineryLabel.setForeground(Color.GRAY);
        wineryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(wineryLabel);
        rightPanel.add(Box.createVerticalStrut(8));

        // 와인 이름
        String htmlTitle = "<html><body style='width: 300px'>" + wine.getName() + " " + wine.getYear() + "</body></html>";
        JLabel nameLabel = new JLabel(htmlTitle);
        nameLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 26)); 
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(nameLabel);
        rightPanel.add(Box.createVerticalStrut(25));

        // 스펙 패널
        JPanel specPanel = new JPanel(new GridBagLayout());
        specPanel.setBackground(Color.WHITE);
        specPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 20); 

        addSpecRow(specPanel, gbc, 0, "Type", wine.getType());
        addSpecRow(specPanel, gbc, 1, "Region", wine.getCountry());
        addSpecRow(specPanel, gbc, 2, "Grapes", wine.getGrape());
        addSpecRow(specPanel, gbc, 3, "Alcohol", wine.getAlcohol() + "%");

        rightPanel.add(specPanel);
        rightPanel.add(Box.createVerticalGlue()); // 남은 공간 밀어내기

        // 가격 및 장바구니 추가 버튼
        JPanel cartBox = new JPanel(new GridLayout(2, 1, 0, 5));
        cartBox.setBackground(new Color(248, 248, 248)); 
        cartBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                new EmptyBorder(15, 20, 15, 20) 
        ));
        cartBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        cartBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140)); 
        
        // 가격
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pricePanel.setOpaque(false);
        
        DecimalFormat df = new DecimalFormat("#,###");
        JLabel priceLabel = new JLabel("₩ " + df.format(wine.getPrice()));
        priceLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 26));
        pricePanel.add(priceLabel);
        
        cartBox.add(pricePanel);
        
        // 수량 조절 및 버튼 패널
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        actionPanel.setOpaque(false);

        // 수량 조절 버튼
        JButton btnMinus = createRoundButton("-");
        quantityLabel = new JLabel("1", SwingConstants.CENTER);
        quantityLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        quantityLabel.setPreferredSize(new Dimension(30, 30));
        JButton btnPlus = createRoundButton("+");
        
        // 장바구니 버튼
        JButton btnCart = new JButton(" Add to Cart ");
        btnCart.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCart.setBackground(new Color(30, 30, 30));
        btnCart.setForeground(Color.WHITE);
        btnCart.setFocusPainted(false);
        btnCart.setBorderPainted(false);
        btnCart.setPreferredSize(new Dimension(140, 35));
        btnCart.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 이벤트 리스너 연결
        btnMinus.addActionListener(e -> {
            if (quantity > 1) {
                quantity--;
                quantityLabel.setText(String.valueOf(quantity));
            }
        });
        
        btnPlus.addActionListener(e -> {
            int currentInCart = getCurrentCartQuantity(wine);
            if (quantity + currentInCart < wine.getStock()) {
                quantity++;
                quantityLabel.setText(String.valueOf(quantity));
            } else {
                JOptionPane.showMessageDialog(this, "재고가 부족합니다.");
            }
        });

        btnCart.addActionListener(e -> addToCart());

        actionPanel.add(btnMinus);
        actionPanel.add(quantityLabel);
        actionPanel.add(btnPlus);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(btnCart);

        cartBox.add(actionPanel, BorderLayout.EAST);
        rightPanel.add(cartBox);

        add(rightPanel, BorderLayout.CENTER);
    }

    // 수량 +/- 버튼 모양
    private JButton createRoundButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(35, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void addSpecRow(JPanel panel, GridBagConstraints gbc, int row, String title, String value) {
        gbc.gridy = row;
        
        gbc.gridx = 0;
        gbc.weightx = 0.15;
        JLabel tLabel = new JLabel(title);
        tLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 14));
        tLabel.setForeground(new Color(100, 100, 100));
        panel.add(tLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.85;
        JLabel vLabel = new JLabel(value);
        vLabel.setFont(new Font("Noto Sans KR", Font.PLAIN, 14));
        panel.add(vLabel, gbc);
    }

    private int getCurrentCartQuantity(Wine wine) {
        for (CartProduct cp : CartManage.getCartList()) {
            if (cp.getProduct().getName().equals(wine.getName())) {
                return cp.getQuantity();
            }
        }
        return 0;
    }

    private void addToCart() {
        int currentInCart = getCurrentCartQuantity(wine);
        
        if (currentInCart + quantity > wine.getStock()) {
            JOptionPane.showMessageDialog(this, "재고가 부족합니다. (남은 재고: " + wine.getStock() + "개)", "알림", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CartManage.addCart(wine, quantity);
        
        int choice = JOptionPane.showConfirmDialog(this, 
                wine.getName() + " " + quantity + "개를 담았습니다.\n계속 쇼핑하시겠습니까?", 
                "알림", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.NO_OPTION) {
            dispose();
        }
    }
}