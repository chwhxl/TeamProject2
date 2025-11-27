package WineShop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Search extends JPanel {

    private JTextField itSearch;
    private JButton itButton;
    private JButton homeButton;

    private JButton topCate1, topCate2;
    private JButton totalButton;
    private JButton home;
    private JPanel resultPanel;

    private JPanel mainPanel;

    public Search(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        
        // 홈 버튼
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        homeButton = new JButton("홈");
        homeButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        homeButton.addActionListener(e -> gotoMain());
        homePanel.add(homeButton);

        // 샵 로고
        JLabel shopLogo = new JLabel("> 가나디 쇼핑몰 <", SwingConstants.CENTER);
        
        shopLogo.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        // 검색
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        itSearch = new JTextField("검색어를 입력하세요.", 13);  // 검색창에 떠있을 멘트
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

        itButton = new JButton("검색");
        itButton.addActionListener(e -> searchName());

        searchPanel.add(itSearch);
        searchPanel.add(itButton);

        // 카테고리+상품
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topCate1 = new JButton("생산지");
        topCate2 = new JButton("종류");
        totalButton = new JButton("모든 상품");
        
        totalButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        totalButton.addActionListener(e -> showAllItem());

        categoryPanel.add(topCate1);
        categoryPanel.add(topCate2);
        categoryPanel.add(totalButton);

        // 버튼 위치 2행 1열
        JPanel bigTopPanel = new JPanel(new GridLayout(2, 1));

        // 1행
        JPanel btLocation1 = new JPanel(new BorderLayout());

        btLocation1.add(homePanel, BorderLayout.WEST);
        btLocation1.add(shopLogo, BorderLayout.CENTER);
        btLocation1.add(new JPanel(), BorderLayout.EAST);
        
        bigTopPanel.add(btLocation1);

        // 2행
        JPanel btLocation2 = new JPanel(new BorderLayout());

        btLocation2.add(categoryPanel, BorderLayout.WEST);
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btLocation2.add(searchPanel, BorderLayout.EAST);

        bigTopPanel.add(btLocation2);

        add(bigTopPanel, BorderLayout.NORTH);

        // 카테고리 하위 버튼
        JPopupMenu prodMenu = new JPopupMenu();
        String[] prods = {"FR", "IT", "SP"};

        for (String prodItem : prods) {
            JMenuItem item = new JMenuItem(prodItem);
            prodMenu.add(item);

            item.addActionListener(e -> filterProd(prodItem));
        }

        topCate1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                prodMenu.show(topCate1, 0, topCate1.getHeight());
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

        topCate2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                typeMenu.show(topCate2, 0, topCate2.getHeight());
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
    
    // 메인으로 돌아가기
    private void gotoMain() {
    	
    }
    
    private void showAllItem() {

        // 이전 검색 결과 삭제
        resultPanel.removeAll();
        // 전체 아이템 출력
        for (Item item : ItemDB.itemList) {
            // 카드 생성
            SearchResult card = new SearchResult(item);
            // 카드 클릭 → 디테일 페이지 이동
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    moveToDetail(item);
                }
            });
            resultPanel.add(card);
        }
        // 화면 갱신
        resultPanel.revalidate();
        resultPanel.repaint();
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
