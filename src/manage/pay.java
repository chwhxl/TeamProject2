package manage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;

import mallmain.Main;
import wineshop.WineList;

public class pay extends JPanel {

    private final Main mainFrame;           // 카드 전환용 프레임
    private JPanel listPanel;               // 상품 리스트 영역
    private JLabel totalLabel;              // 총액 표시 레이블
    private List<Integer> check = new ArrayList<>();

    public pay(Main mainFrame) {
        this.mainFrame = mainFrame;
        initUI();        // 전체 화면 골격 구성
        rebuildList();   // 상품 리스트 실제로 그림
    }
 // 체크한 것만 가격 계산하는 메서드
    private void updateTotalPrice() {
        var wineList = WineList.getWineList();
        if (wineList == null) return;

        int total = 0;
        for (int idx : check) {              // 체크된 인덱스만 합산
            if (idx >= 0 && idx < wineList.size()) {
                int price = wineList.get(idx).getPrice();
                int stock = wineList.get(idx).getStock(); // 필요에 따라 1로 바꿀 수도 있음
                total += price * stock;
            }
        }
        totalLabel.setText("총 가격: " + total + "원");
    }

    /** 화면 기본 구조 (제목 / 리스트 / 하단 버튼들) 구성 */
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ---------- 상단 제목 ----------
        JLabel titleLabel = new JLabel("Wine Shop", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // ---------- 가운데 리스트(스크롤) ----------
        listPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scroll = new JScrollPane(
                listPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        add(scroll, BorderLayout.CENTER);

        // ---------- 하단 버튼/정보 바 ----------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        totalLabel = new JLabel("총 가격: 0원");

        JButton historyButton = new JButton("구매내역");
        JButton payButton     = new JButton("결제");
        JButton homeButton    = new JButton("홈으로");

        // 결제 버튼 동작
        payButton.addActionListener(e -> handlePay());

        // 구매내역 화면 전환 (카드 이름은 Main 쪽 설계에 맞춰서 사용)
        historyButton.addActionListener(e -> mainFrame.showMainCard("HISTORY"));

        // 홈 화면 전환
        homeButton.addActionListener(e -> mainFrame.showMainCard("HOME"));

        bottomPanel.add(totalLabel);
        bottomPanel.add(historyButton);
        bottomPanel.add(payButton);
        bottomPanel.add(homeButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /** 상품 리스트 + 체크박스 + 라벨들을 다시 생성 */
    private void rebuildList() {
        listPanel.removeAll();
        check.clear();

        var wineList = WineList.getWineList();
        if (wineList == null || wineList.isEmpty()) {
            listPanel.add(new JLabel("장바구니에 담긴 상품이 없습니다."));
            listPanel.revalidate();
            listPanel.repaint();
            totalLabel.setText("총 가격: 0원");
            return;
        }

        Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY);
        Dimension checkSize = new Dimension(60, 100);
        Dimension btnSize   = new Dimension(100, 100);
        Dimension lblSize   = new Dimension(250, 100);

        for (int i = 0; i < wineList.size(); i++) {
            final int index = i;

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));

            // ---------- 체크박스 ----------
            JCheckBox checkBox = new JCheckBox();
            checkBox.addItemListener(e -> {
                if (checkBox.isSelected()) {
                    check.add(index);
                } else {
                    check.remove(Integer.valueOf(index));
                }
                updateTotalPrice();      // ★ 체크 상태가 바뀔 때마다 총액 갱신
            });

            JPanel checkPanel = new JPanel(new GridBagLayout());
            checkPanel.setPreferredSize(checkSize);
            checkPanel.setBorder(border);
            checkPanel.add(checkBox);

            // ---------- 이미지 버튼 ----------
            JButton imgBtn = new JButton(
                    new ImageIcon(wineList.get(index).getImgPath())
            );
            imgBtn.setBorderPainted(false);
            imgBtn.setFocusPainted(false);
            imgBtn.setContentAreaFilled(false);
            imgBtn.setOpaque(false);

            JPanel imgPanel = new JPanel(new GridBagLayout());
            imgPanel.setPreferredSize(btnSize);
            imgPanel.setBorder(border);
            imgPanel.add(imgBtn);

            imgBtn.addActionListener(e -> {
                System.out.println("이미지 클릭: " + wineList.get(index).getName());
            });

            // ---------- 이름 버튼 ----------
            JButton nameBtn = new JButton(wineList.get(index).getName());
            nameBtn.setBorderPainted(false);
            nameBtn.setFocusPainted(false);
            nameBtn.setContentAreaFilled(false);
            nameBtn.setOpaque(false);

            JPanel namePanel = new JPanel(new GridBagLayout());
            namePanel.setPreferredSize(btnSize);
            namePanel.setBorder(border);
            namePanel.add(nameBtn);

            nameBtn.addActionListener(e -> {
                System.out.println("이름 클릭: " + wineList.get(index).getName());
            });

            // ---------- 가격/재고 라벨 ----------
            int price = wineList.get(index).getPrice();
            int stock = wineList.get(index).getStock();
            JLabel lbl = new JLabel("가격: " + price + "원, 재고: " + stock + "개");

            JPanel lblPanel = new JPanel(new GridBagLayout());
            lblPanel.setPreferredSize(lblSize);
            lblPanel.setBorder(border);
            lblPanel.add(lbl);

            itemPanel.add(checkPanel);
            itemPanel.add(imgPanel);
            itemPanel.add(namePanel);
            itemPanel.add(lblPanel);

            listPanel.add(itemPanel);
        }

        // 처음에는 아무 것도 체크 안 되어 있으니 0원으로 초기화
        totalLabel.setText("총 가격: 0원");

        listPanel.revalidate();
        listPanel.repaint();
    }

    /** 결제 처리 로직 */
    private void handlePay() {
        int result = JOptionPane.showConfirmDialog(
                pay.this,
                "결제를 진행하시겠습니까?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (result != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(pay.this, "결제 취소.");
            return;
        }

        if (check.isEmpty()) {
            JOptionPane.showMessageDialog(pay.this, "선택된 상품이 없습니다.");
            return;
        }
      //결제창 수정
    	JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5)); // 2줄 2칸 그리드
    	JLabel nameLabel = new JLabel("이름:");
    	JTextField nameField = new JTextField();
    	JLabel phoneLabel = new JLabel("전화번호:");
    	JTextField phoneField = new JTextField();

    	inputPanel.add(nameLabel);
    	inputPanel.add(nameField);
    	inputPanel.add(phoneLabel);
    	inputPanel.add(phoneField);

    	int result1 = JOptionPane.showConfirmDialog(
    	        pay.this, 
    	        inputPanel, 
    	        "결제 정보 입력", 
    	        JOptionPane.OK_CANCEL_OPTION
    	);

    	if (result1 == JOptionPane.OK_OPTION) {
    	    String userName = nameField.getText();
    	    String userPhone = phoneField.getText();

    	    // 입력값 검증 (비어있으면 진행 안 함)
    	    if (userName.isEmpty() || userPhone.isEmpty()) {
    	        JOptionPane.showMessageDialog(pay.this, "정보를 모두 입력해주세요.");
    	        return; 
    	    }

    	    // 2. 로딩 창 띄우기 (4초)
    	    // JOptionPane을 이용해 다이얼로그 생성 (Progress Bar 포함)
    	    JProgressBar progressBar = new JProgressBar();
    	    progressBar.setIndeterminate(true); // 왔다갔다 하는 로딩바
    	    
    	    JOptionPane loadingPane = new JOptionPane(
    	            "결제 승인 중입니다... 잠시만 기다려주세요.", 
    	            JOptionPane.INFORMATION_MESSAGE, 
    	            JOptionPane.DEFAULT_OPTION, 
    	            null, 
    	            new Object[]{}, // 버튼 없애기
    	            null
    	    );
    	    
    	    JDialog loadingDialog = loadingPane.createDialog(pay.this, "결제 진행 중");
    	    
    	    // 4초 뒤에 실행할 타이머 설정
    	    Timer timer = new Timer(4000, new ActionListener() {
    	        @Override
    	        public void actionPerformed(ActionEvent e) {
    	            loadingDialog.dispose(); // 로딩 창 닫기
    	            
    	        }
    	    });
    	    
    	    timer.setRepeats(false); // 한 번만 실행
    	    timer.start(); // 타이머 시작
    	    
    	    loadingDialog.setVisible(true); // 로딩 창 보여주기 (여기서 코드 대기)
    	}
        var wineList = WineList.getWineList();
        int tp = 0;

        // 뒤에서 앞으로 돌면서 삭제/결제 처리
        for (int j = check.size() - 1; j >= 0; j--) {
            int idx = check.get(j);          // 실제 와인 인덱스

            //tp += payments(idx);     // 결제 (결제 금액 반환한다고 가정)

            if (wineList.get(idx).getStock() == 0) {
                wineList.remove(idx);        // 재고 0이면 목록에서 제거
            }
        }

        JOptionPane.showMessageDialog(pay.this, tp + " 원 결제가 완료되었습니다.");

        // 결제 후 화면 다시 구성
        rebuildList();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 700);
    }
}
