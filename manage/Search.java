package proj2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Search extends JPanel {

    private JTextField itSearch;
    private JButton cateSearch;

    private JButton topCate, bottomCate;
    private JPanel resultPanel;

    private JPanel mainPanel;

    public Search(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        // 검색
        JPanel topPanel = new JPanel();

        itSearch = new JTextField("검색어를 입력하세요.", 20);  // 검색창에 떠있을 멘트
        itSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {  // FocusListener 인터페이스
                if (itSearch.getText().equals("검색어를 입력하세요.")) {
                    itSearch.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {  // FocusListener 인터페이스
                if (itSearch.getText().isBlank()) {
                    itSearch.setText("검색어를 입력하세요.");
                }
            }
        });

        cateSearch = new JButton("검색");
        cateSearch.addActionListener(e -> searchName());

        topPanel.add(itSearch);
        topPanel.add(cateSearch);

        // 카테고리
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topCate = new JButton("생산지");
        bottomCate = new JButton("종류");

        categoryPanel.add(topCate);
        categoryPanel.add(bottomCate);

        // 버튼 위치
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(categoryPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        // 카테고리 하위 버튼
        JPopupMenu prodMenu = new JPopupMenu();
        String[] prods = {"FR", "IT", "SP"};

        for (String prodItem : prods) {
            JMenuItem item = new JMenuItem(prodItem);
            prodMenu.add(item);

            item.addActionListener(e -> filterProd(prodItem));
        }

        topCate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                prodMenu.show(topCate, 0, topCate.getHeight());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Timer t = new Timer(120, ev -> {
                    if (!prodMenu.isShowing()) {
                        prodMenu.setVisible(false);
                    }
                });
                t.setRepeats(false);
                t.start();
            }
        });

        // 카테고리 하위 버튼
        JPopupMenu typeMenu = new JPopupMenu();
        String[] types = {"레드", "화이트", "스파클링"};

        for (String type : types) {
            JMenuItem item = new JMenuItem(type);
            typeMenu.add(item);

            item.addActionListener(e -> filterType(type));
        }

        bottomCate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                typeMenu.show(bottomCate, 0, bottomCate.getHeight());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Timer t = new Timer(120, ev -> {
                    if (!typeMenu.isShowing()) {
                        typeMenu.setVisible(false);
                    }
                });
                t.setRepeats(false);
                t.start();
            }
        });

        // 결과
        resultPanel = new JPanel(new FlowLayout());
        add(new JScrollPane(resultPanel), BorderLayout.CENTER);
    }

    // 상품 검색
    private void searchName() {
        String key = itSearch.getText().trim().toLowerCase();  // 대소문자 구별 없음
        updateResult(item -> item.getName().toLowerCase().contains(key));
    }

    // 생산지
    private void filterProd(String itemProd) {
        updateResult(item -> item.getOrigin().equals(itemProd));  // item class getter
    }

    // 종류
    private void filterType(String type) {
        updateResult(item -> item.getType().equals(type));  // item class getter
    }

    // 검색 결과, 카테고리 선택 결과
    private void updateResult(ItemFilter filter) {
        resultPanel.removeAll();

        for (Item item : ItemDB.itemList) {  // item/itemDB class
            if (!filter.match(item)) continue;

            SearchResult card = new SearchResult(item);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    moveToDetail(item);
                }
            });

            resultPanel.add(card);
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    // 상세 페이지
    private void moveToDetail(Item item) {  // 임시 상세 페이지 이동
        mainPanel.removeAll();
        mainPanel.add(new DetailPanel(item));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // 인터페이스
    private interface ItemFilter {
        boolean match(Item item);
    }

}

