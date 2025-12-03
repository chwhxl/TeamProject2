package mallmain;

import liquorshop.Liquor;
import liquorshop.LiquorList;
import liquorshop.LiquorSearchResult;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

class LiquorShopPanel extends JPanel {
	private JTextField itSearch;
    private JButton itButton;
    private JButton homeButton;
    private JButton cartButton;
    private JButton historyButton;
    private JButton shopLogo = new JButton(" 가나디 위스키창고 "); //샵 로고 나오면 이미지로 대체 가능

    private JButton topCate1;
    private JButton totalButton;
    private JPanel resultPanel;
    private JPanel mainPanel;
    private Main mainFrame;
    private JScrollPane scrollPane;
	
    public LiquorShopPanel(Main mainFrame) {
    	// 리큐르 데이터 로드
    	LiquorList.loadLiquorData();
    
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
        
        // 장바구니, 히스토리 버튼
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
       
        
        // 샵 로고 누르면 shopmain으로 이동
        shopLogo.setBorderPainted(false);   
        shopLogo.setContentAreaFilled(false); 
        shopLogo.setMargin(new Insets(0, 0, 0, 0)); 


        shopLogo.setFont(Main.getCustomFont("나눔손글씨 중학생.ttf", 40));
        shopLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));  // 버튼 위로 가면 커서 모양 변경
        
        shopLogo.addActionListener(e -> mainFrame.showMainCard("LIQUOR"));
        // 검색
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);

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
        Main.MyFont(itButton);
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
        totalButton.addActionListener(e -> showAllLiquor());

        categoryPanel.add(totalButton);
        categoryPanel.add(topCate1);

        // 버튼 3 X 1 배열
        JPanel bigTopPanel = new JPanel();
        bigTopPanel.setLayout(new BoxLayout(bigTopPanel, BoxLayout.Y_AXIS));

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

        // 카테고리 분류
        JPopupMenu typeMenu = new JPopupMenu();
        String[] types = {"Whisky", "Tequila", "Vodka", "Rum"};

        for (String type : types) {
            JMenuItem Liquor = new JMenuItem(type);
            typeMenu.add(Liquor);

            Liquor.setCursor(new Cursor(Cursor.HAND_CURSOR));
            Liquor.addActionListener(e -> filterType(type));  // filterProd
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
        showAllLiquor();
        resultPanel.revalidate();
        resultPanel.repaint();
    }
    
    // 전체 상품 보여주기
    private void showAllLiquor() {
        resultPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 40, 20);
        gbc.anchor = GridBagConstraints.NORTH;   // 카드 상단 고정

        int col = 0;
        int row = 0;

        for (Liquor Liquor : LiquorList.getLiquorList()) {

            LiquorSearchResult card = new LiquorSearchResult(Liquor);

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
        updateResult(Liquor -> Liquor.getName().toLowerCase().contains(key));
    }
    // 종류
    private void filterType(String type) {
    	updateResult(Liquor -> Liquor.getType().trim().equalsIgnoreCase(type.trim()));
    }

    // 검색 결과, 카테고리 선택 결과
    private void updateResult(LiquorFilter filter) {
        resultPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 40, 20);
        gbc.anchor = GridBagConstraints.NORTH;   // 카드 상단 고정

        int col = 0;
        int row = 0;

        for (Liquor Liquor : LiquorList.getLiquorList()) {
            if (!filter.match(Liquor)) continue;

            LiquorSearchResult card = new LiquorSearchResult(Liquor);

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
		showAllLiquor();
	}
    
    // 인터페이스
    private interface LiquorFilter {
        boolean match(Liquor Liquor);
    }
}
