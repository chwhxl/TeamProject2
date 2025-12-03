package mallmain;

import wineshop.Wine;
import wineshop.WineList;
import wineshop.WineSearchResult;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

class WineShopPanel extends JPanel {
	private JTextField itSearch;
    private JButton itButton;
    private JButton homeButton;
    private JButton cartButton;
    private JButton historyButton;
    private JButton shopLogo = new JButton(" 가나디 포도창고 "); //샵 로고 나오면 이미지로 대체 가능

    private JButton topCate1;
    private JButton totalButton;
    private JPanel resultPanel;
    private JPanel mainPanel;
    private Main mainFrame;
    private JScrollPane scrollPane;
	
    public WineShopPanel(Main mainFrame) {
    	// 와인 데이터 로드
    	WineList.loadWineData();
    
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // 홈 버튼
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        homePanel.setBackground(Color.WHITE);

        homeButton = new JButton("  홈  ");
        Main.MyFont(homeButton);
        homeButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}
 
            @Override
            public void focusLost(FocusEvent e) {}
        });

        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.addActionListener(e -> mainFrame.showMainCard("HOME"));
        homePanel.add(homeButton);
        
        // 장바구니 버튼 + history -> 장바구니 이동 수정
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartPanel.setBackground(Color.WHITE);
        
        cartButton = new JButton(" 장바구니 ");
		Main.MyFont(cartButton);
        cartButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {}
        });
        

        historyButton = new JButton("구매 이력");
        Main.MyFont(historyButton);
        historyButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        cartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        historyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartButton.addActionListener(e -> mainFrame.showMainCard("CART"));
        historyButton.addActionListener(e -> mainFrame.showMainCard("HISTORY"));
        cartPanel.add(historyButton);
        cartPanel.add(cartButton);
       
        
        // 샵 로고 누르면 홈 패널로 전환 shopmain으로 이동
        shopLogo.setBorderPainted(false);  
        shopLogo.setContentAreaFilled(false); 
        shopLogo.setMargin(new Insets(0, 0, 0, 0));

        shopLogo.setFont(Main.getCustomFont("나눔손글씨 중학생.ttf", 40));
        shopLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));  // 버튼 위로 가면 커서 모양 변경
        
        shopLogo.addActionListener(e -> mainFrame.showMainCard("WINE"));
        // 검색
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);

        itSearch = new JTextField("검색어를 입력하세요.", 20);  // 검색창에 떠있을 멘트
        itSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {  
                if (itSearch.getText().equals("검색어를 입력하세요.")) {
                    itSearch.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {  
                if (itSearch.getText().isBlank()) {
                    itSearch.setText("검색어를 입력하세요.");
                }
            }
        });

        itButton = new JButton("검색");
        Main.MyFont(itButton);
        itButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        itButton.addActionListener(e -> searchName());

        searchPanel.add(itSearch);
        searchPanel.add(itButton);

        // 카테고리+전체 상품
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.setBackground(Color.WHITE);

        totalButton = new JButton("모든 상품");
        Main.MyFont(totalButton);
        topCate1 = new JButton("종류");
        Main.MyFont(topCate1);
        
        totalButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        totalButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        totalButton.addActionListener(e -> showAllWine());

        categoryPanel.add(totalButton);
        categoryPanel.add(topCate1);

        // 버튼 3 X 1 배열
        JPanel bigTopPanel = new JPanel();
        bigTopPanel.setLayout(new BoxLayout(bigTopPanel, BoxLayout.Y_AXIS));
        bigTopPanel.setBackground(Color.WHITE);

        // 로고(샵메인) 버튼
        JPanel btLocation1 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        btLocation1.setBackground(Color.WHITE);
        btLocation1.add(shopLogo);
       
        bigTopPanel.add(btLocation1);
        
        // 홈, 장바구니 버튼
        JPanel btLocation2 = new JPanel(new BorderLayout());
        btLocation2.setBackground(Color.WHITE);
        btLocation2.add(homePanel, BorderLayout.WEST);
        btLocation2.add(cartPanel, BorderLayout.EAST);
        
        bigTopPanel.add(btLocation2);

        // 카테고리, 상품 버튼
        JPanel btLocation3 = new JPanel(new BorderLayout());
        btLocation3.setBackground(Color.WHITE);
        btLocation3.add(categoryPanel, BorderLayout.WEST);
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btLocation3.add(searchPanel, BorderLayout.EAST);

        bigTopPanel.add(btLocation3);
        
        Color lineColor = new Color(220, 220, 220);
        bigTopPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, lineColor));
        add(bigTopPanel, BorderLayout.NORTH);

        // 카테고리 하위 버튼 - 생산지
        JPopupMenu typeMenu = new JPopupMenu();
        String[] types = {"RED", "WHITE", "SPARKLING"};

        for (String type : types) {
            JMenuItem Wine = new JMenuItem(type);
            typeMenu.add(Wine);

            Wine.setCursor(new Cursor(Cursor.HAND_CURSOR));
            Wine.addActionListener(e -> filterType(type));  // filterProd
        }

        topCate1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                typeMenu.show(topCate1, 0, topCate1.getHeight());
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
        resultPanel.setBackground(Color.WHITE);
        
        this.scrollPane = new JScrollPane(resultPanel);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        showAllWine();
        resultPanel.revalidate();
        resultPanel.repaint();
    }
    
    // 전체 상품 showing
    private void showAllWine() {
        resultPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 40, 20);
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
        Main.resetScroll(this.scrollPane);
    }



    // 상품 검색
    private void searchName() {
        String key = itSearch.getText().trim().toLowerCase();  // 대소문자 구별 없음
        updateResult(Wine -> Wine.getName().toLowerCase().contains(key));
    }
    // 종류
    private void filterType(String type) {
    	updateResult(Wine -> Wine.getType().trim().equalsIgnoreCase(type.trim()));
    }

    // 검색 결과, 카테고리 선택 결과
    private void updateResult(WineFilter filter) {
        resultPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 40, 20);
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
        Main.resetScroll(this.scrollPane);
    }
    
    public void refreshShop() {
		showAllWine();
	}
    // 인터페이스
    private interface WineFilter {
        boolean match(Wine Wine);
    }
}
