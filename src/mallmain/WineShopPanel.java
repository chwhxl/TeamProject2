package mallmain;

import javax.swing.*;

import wineshop.Wine;
import wineshop.WineList;

import java.awt.*;
import java.awt.event.*;

class WineShopPanel extends JPanel {
	private JTextField itSearch;
    private JButton itButton;
    private JButton homeButton;
    private JButton cartButton;

    private JButton topCate1, topCate2;
    private JButton totalButton;
    private JPanel resultPanel;

    private JPanel mainPanel;
    private Main mainFrame;

    public WineShopPanel(Main mainFrame) {
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
        
        // 장바구니 버튼 -> 장바구니 이동 수정
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        cartButton = new JButton("장바구니");
        cartButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        cartButton.addActionListener(e -> gotoCart());
        cartPanel.add(cartButton);

        // 샵 로고 -> 버튼으로 변경, shopmain으로 이동
        JLabel shopLogo = new JLabel("> 가나디 포도밭 <", SwingConstants.CENTER);
        
        shopLogo.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        // 검색
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        itSearch = new JTextField("검색어를 입력하세요.", 15);  // 검색창에 떠있을 멘트
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

        // 카테고리 하위 버튼
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
        resultPanel = new JPanel(new FlowLayout());
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
    
    private void showAllWine() {
        // 이전 검색 결과 삭제
        resultPanel.removeAll();
        // 전체 아이템 출력
        for (Wine Wine : WineList.getWineList()) {
            // 카드 생성
            // WineSearchResult card = new WineSearchResult(Wine);
            // 카드 클릭 → 디테일 페이지 이동
            // card.addMouseListener(new MouseAdapter() {
                // @Override
                // public void mouseClicked(MouseEvent e) {
                    // moveToDetail(Wine);
                // }
            // });
            // resultPanel.add(card);
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
        updateResult(Wine -> Wine.getCountry().equals(WineProd));  // Wine class getter
    }

    // 종류
    private void filterType(String type) {
        updateResult(Wine -> Wine.getType().equals(type));  // Wine class getter
    }

    // 검색 결과, 카테고리 선택 결과
    private void updateResult(WineFilter filter) {
        resultPanel.removeAll();
        
        /*
        for (Wine Wine : WineList.getWineList()) {  // Wine/WineList class
            if (!filter.match(Wine)) continue;

            WineSearchResult card = new WineSearchResult(Wine);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    moveToDetail(Wine);
                }
            });

            resultPanel.add(card);
        }
        */

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    // 상세 페이지
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
	/*
    public ShopPanel(Main mainFrame, String shopName, Color bgColor) {
    	
    	setLayout(new BorderLayout());
    	setBackground(bgColor);

        // 상단: 상점 이름
        JLabel titleLabel = new JLabel(shopName + "에 오신 것을 환영합니다", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 중앙: 상점 내용 (상태 유지가 잘 되는지 테스트하는 곳)
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false); // 투명하게 해서 배경색 보이게
        contentPanel.setLayout(new FlowLayout());

        contentPanel.add(new JLabel("장바구니 메모: "));
        JTextField memoField = new JTextField(15); // 여기에 글을 쓰고 뒤로가기를 눌러보세요.
        contentPanel.add(memoField);
        
        JCheckBox checkBox = new JCheckBox("쿠폰 적용하기");
        checkBox.setOpaque(false);
        contentPanel.add(checkBox);

        add(contentPanel, BorderLayout.CENTER);

        // 하단: 뒤로가기 버튼
        JButton btnBack = new JButton("홈으로 돌아가기");
        btnBack.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        btnBack.setPreferredSize(new Dimension(100, 50));
        
        // 클릭하면 다시 HOME 화면을 보여달라고 요청
        btnBack.addActionListener(e -> mainFrame.showMainCard("HOME"));
        
        add(btnBack, BorderLayout.SOUTH);
    }
    */
}
