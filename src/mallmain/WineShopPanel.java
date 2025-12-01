package mallmain;

import javax.swing.*;

import wineshop.Wine;
import wineshop.WineList;
import wineshop.WineSearchResult;

import java.awt.*;
import java.awt.event.*;

class WineShopPanel extends JPanel {
	private JTextField itSearch;
    private JButton itButton;
    private JButton homeButton;
    private JButton cartButton;
    private JButton historyButton;

    private JButton topCate1, topCate2;
    private JButton totalButton;
    private JPanel resultPanel;

    private JPanel mainPanel;
    private Main mainFrame;

    public WineShopPanel(Main mainFrame) {
    	// 와인 데이터 로드
    	WineList.loadWineData();
    	
    	// 홈 버튼 연결
        this.mainFrame = mainFrame;
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

        homeButton.addActionListener(e -> mainFrame.showMainCard("HOME"));
        homePanel.add(homeButton);
        
        // 장바구니 버튼 + history -> 장바구니 이동 수정
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        cartButton = new JButton("장바구니");
        cartButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {}
        });
        
        historyButton = new JButton("히스토리");
        historyButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        cartButton.addActionListener(e -> gotoCart());
        historyButton.addActionListener(e -> mainFrame.showMainCard("HISTORY"));
        cartPanel.add(historyButton);
        cartPanel.add(cartButton);

        // 샵 로고 -> 버튼으로 변경, shopmain으로 이동
        JLabel shopLogo = new JLabel("> 가나디 포도밭 <", SwingConstants.CENTER);
        
        shopLogo.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        // 검색
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

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

        itButton = new JButton("검색");
        itButton.addActionListener(e -> searchName());

        searchPanel.add(itSearch);
        searchPanel.add(itButton);

        // 카테고리+전체 상품
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        totalButton = new JButton("모든 상품");
        topCate1 = new JButton("생산지");
        topCate2 = new JButton("종류");
        
        totalButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        totalButton.addActionListener(e -> showAllWine());

        categoryPanel.add(totalButton);
        categoryPanel.add(topCate1);
        categoryPanel.add(topCate2);

        // 버튼 위치 3행 1열
        JPanel bigTopPanel = new JPanel(new GridLayout(3, 1));

        // 1행: 로고(샵메인) 버튼
        JPanel btLocation1 = new JPanel(new BorderLayout());

        btLocation1.add(shopLogo, BorderLayout.CENTER);
        
        bigTopPanel.add(btLocation1);
        
        // 2행: 홈, 장바구니 버튼
        JPanel btLocation2 = new JPanel(new BorderLayout());
        
        btLocation2.add(homePanel, BorderLayout.WEST);
        btLocation2.add(cartPanel, BorderLayout.EAST);
        
        bigTopPanel.add(btLocation2);

        // 3행: 카테고리, 상품 버튼
        JPanel btLocation3 = new JPanel(new BorderLayout());
        
        btLocation3.add(categoryPanel, BorderLayout.WEST);
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btLocation3.add(searchPanel, BorderLayout.EAST);

        bigTopPanel.add(btLocation3);

        add(bigTopPanel, BorderLayout.NORTH);

        // 카테고리 하위 버튼 - 생산지
        JPopupMenu prodMenu = new JPopupMenu();
        String[] country = {"FRANCE", "ITALY", "SPAIN"};

        for (String prodWine : country) {
            JMenuItem Wine = new JMenuItem(prodWine);
            prodMenu.add(Wine);

            Wine.addActionListener(e -> filterProd(prodWine));
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
        String[] types = {"RED", "WHITE", "SPARKLING"};

        for (String type : types) {
            JMenuItem Wine = new JMenuItem(type);
            typeMenu.add(Wine);

            Wine.addActionListener(e -> filterType(type));
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
        resultPanel = new JPanel(new GridBagLayout());
        add(new JScrollPane(resultPanel), BorderLayout.CENTER);
        resultPanel.revalidate();
        resultPanel.repaint();
    }

	// 장바구니 가기
    private void gotoCart() {
    	// Cart cartPanel = new MallMain();
    	// Panel.removeAll();                        
    	// Panel.add(cartPanel);
    	// Panel.revalidate();
    	// Panel.repaint();
    }
    
    // 전체 상품 showing
    private void showAllWine() {
        resultPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.NORTH;   // 카드 상단 고정

        int col = 0;
        int row = 0;

        for (Wine wine : WineList.getWineList()) {

            WineSearchResult card = new WineSearchResult(wine);

            gbc.gridx = col;
            gbc.gridy = row;

            resultPanel.add(card, gbc);

            col++;
            if (col == 5) {
                col = 0;
                row++;
            }
        }

        // 화면 갱신
        resultPanel.revalidate();
        resultPanel.repaint();
    }



    // 상품 검색
    private void searchName() {
        String key = itSearch.getText().trim().toLowerCase();  // 대소문자 구별 없음
        updateResult(Wine -> Wine.getName().toLowerCase().contains(key));
    }

    // 생산지
    private void filterProd(String WineProd) {
    	updateResult(Wine -> Wine.getCountry().trim().equalsIgnoreCase(WineProd.trim()));
    }

    // 종류
    private void filterType(String type) {
    	updateResult(Wine -> Wine.getType().equalsIgnoreCase(type.trim()));
    }

    // 검색 결과, 카테고리 선택 결과
    private void updateResult(WineFilter filter) {
        resultPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.NORTH;   // 카드 상단 고정

        int col = 0;
        int row = 0;

        for (Wine wine : WineList.getWineList()) {
            if (!filter.match(wine)) continue;

            WineSearchResult card = new WineSearchResult(wine);

            gbc.gridx = col;
            gbc.gridy = row;
            resultPanel.add(card, gbc);

            col++;
            if (col == 5) {
                col = 0;
                row++;
            }
        }

        // 화면 갱신
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    // 상세 페이지 -> 구현 필요
    private void moveToDetail(Wine Wine) {  // 임시 상세 페이지 이동
        mainPanel.removeAll();
        // mainPanel.add(new DetailPanel(Wine));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // 인터페이스
    private interface WineFilter {
        boolean match(Wine Wine);
    }
}
