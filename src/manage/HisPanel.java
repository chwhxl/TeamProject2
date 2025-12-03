package manage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import mallmain.Main;

public class HisPanel extends JPanel {

    private JPanel hispanel; 
    private Main mainFrame;

    public HisPanel(Main mainFrame) {
        this.mainFrame = mainFrame;

        // 전체 좌우 40px 여백
        this.setBorder(new EmptyBorder(0, 40, 0, 40));

        setLayout(new BorderLayout());

        // 1. 상단 타이틀
        JLabel titleLabel = new JLabel("구매 내역 (영수증)", SwingConstants.CENTER);
        titleLabel.setFont(Main.getCustomFont("나눔손글씨 중학생.ttf", 40));
        titleLabel.setBorder(new EmptyBorder(20, 0, 10, 0));

        // 2. 헤더 영역 
        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setBackground(new Color(245, 245, 245));

        headerPanel.add(makeHeaderLabel("상품명"));
        headerPanel.add(makeHeaderLabel("가격"));
        headerPanel.add(makeHeaderLabel("개수"));
        headerPanel.add(makeHeaderLabel("총 가격"));

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(titleLabel);
        northPanel.add(headerPanel);

        add(northPanel, BorderLayout.NORTH);

        // 3. 리스트
        hispanel = new JPanel();
        hispanel.setLayout(new BoxLayout(hispanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(hispanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);

        // 4. 버튼 영역
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.setBorder(new EmptyBorder(10, 0, 20, 0));

        JPanel buttonPanel = new JPanel();
        JButton btnHome = new JButton("메인으로 돌아가기");
        JButton btnBack = new JButton("쇼핑 계속하기");

        btnHome.addActionListener(e -> mainFrame.showMainCard("HOME"));
        btnBack.addActionListener(e -> mainFrame.goBack());

        buttonPanel.add(btnHome);
        buttonPanel.add(btnBack);
        
        southPanel.add(Box.createVerticalStrut(10));
        southPanel.add(buttonPanel);

        add(southPanel, BorderLayout.SOUTH);

        refreshHistory();
    }

    private JLabel makeHeaderLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        return label;
    }

    // 화면 갱신
    public void refreshHistory() {
        hispanel.removeAll();

        List<HistoryProduct> list = HistoryManage.getHistoryList();

        if (list.isEmpty()) {
            hispanel.add(new JLabel("구매 내역이 없습니다."));
            hispanel.revalidate();
            hispanel.repaint();
            return;
        }

        // 1. 사용자 이름 기준으로 그룹
        HashMap<String, ArrayList<HistoryProduct>> grouped = new HashMap<>();

        for (HistoryProduct item : list) {
            String user = item.getUser();

            if (!grouped.containsKey(user)) {
                grouped.put(user, new ArrayList<>());
            }

            grouped.get(user).add(item);
        }

        // 2. 사용자별 출력
        for (String user : grouped.keySet()) {

            // 사용자 이름 출력
            JLabel userLabel = new JLabel("구매자: " + user);
            userLabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
            userLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
            hispanel.add(userLabel);

            long sum = 0;

            // 상품 출력
            for (HistoryProduct hp : grouped.get(user)) {
                hispanel.add(createItemPanel(hp));
                hispanel.add(Box.createVerticalStrut(2));
                sum += hp.getProductTotalPrice();
            }

            // 사용자별 가격
            JLabel totalLabel = new JLabel(String.format("총 금액: %,d원", sum), SwingConstants.RIGHT);
            totalLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
            totalLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
            hispanel.add(totalLabel);

            hispanel.add(new JSeparator());
        }

        hispanel.revalidate();
        hispanel.repaint();
    }

    // 상품 한 줄 생성 (상하 40px)
    private JPanel createItemPanel(HistoryProduct product) {

        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setBackground(Color.WHITE);

        // 줄 높이 40px 고정
        panel.setPreferredSize(new Dimension(0, 40));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panel.setMinimumSize(new Dimension(0, 40));

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel priceLabel = new JLabel(String.format("%,d원", product.getPrice()));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel quantityLabel = new JLabel(product.getQuantity() + "개");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel totalLabel = new JLabel(String.format("%,d원", product.getProductTotalPrice()));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        priceLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        quantityLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        totalLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        panel.add(nameLabel);
        panel.add(priceLabel);
        panel.add(quantityLabel);
        panel.add(totalLabel);

        return panel;
    }
}
