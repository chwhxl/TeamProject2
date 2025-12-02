package manage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import mallmain.Main;
import wineshop.Wine;

public class CartPanel extends JPanel {

    private final Main mainFrame;
    private JPanel listPanel;
    private JLabel totalLabel;
    
    private JScrollPane scrollPane;
    private JLabel emptyLabel;
    
    // 장바구니에서 선택된 아이템들의 목록 (결제 대상)
    private List<CartProduct> selectedItems = new ArrayList<>();

    public CartPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }

    // 화면이 열릴 때마다 호출되어 최신 장바구니 목록을 보여줘야 함
    public void refreshCart() {
        rebuildList();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 상단 제목
        JLabel titleLabel = new JLabel("장바구니 (Cart)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 리스트 패널
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // 세로 정렬 수정
        
        scrollPane = new JScrollPane(listPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // 장바구니가 비어있을 때 보여줄 라벨
        emptyLabel = new JLabel("장바구니가 비어있습니다.", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        emptyLabel.setForeground(Color.GRAY);
        
        add(scrollPane, BorderLayout.CENTER);

        // 하단 패널
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        totalLabel = new JLabel("선택 상품 총액: 0원");
        
   
        JButton btnPay = new JButton("선택 상품 결제");
        Main.MyFont(btnPay);
        btnPay.setContentAreaFilled(true);  
        btnPay.setBackground(new Color(93,156,89));
        
        JButton btnDelete = new JButton("선택 삭제");
        Main.MyFont(btnDelete);
        btnDelete.setContentAreaFilled(true);
        btnDelete.setBackground(new Color(223,46,56));
        JButton btnBack = new JButton("쇼핑 계속하기");
        Main.MyFont(btnBack);
        btnBack.setBorderPainted(true);
        JButton btnCheck = new JButton("전체 선택/해제");
        Main.MyFont(btnCheck);
        btnCheck.setBorderPainted(true);

        // 이벤트 연결
        btnPay.addActionListener(e -> handlePay());
        btnDelete.addActionListener(e -> handleDelete());
        btnBack.addActionListener(e -> mainFrame.goBack());
        // btnCheck.addActionListener(e -> );

        bottomPanel.add(totalLabel);
        bottomPanel.add(btnCheck);
        bottomPanel.add(btnDelete);
        bottomPanel.add(btnPay);
        bottomPanel.add(btnBack);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void rebuildList() {
        listPanel.removeAll();
        selectedItems.clear(); // 선택 목록 초기화

        // CartManage에서 데이터 가져오기
        List<CartProduct> cartList = CartManage.getCartList();

        // 장바구니 상품 유무에 따라 화면 교체
        if (cartList.isEmpty()) {
        	// 스크롤 패널 제거하고 빈 라벨을 붙임
            remove(scrollPane);
            add(emptyLabel, BorderLayout.CENTER);
            totalLabel.setText("선택 상품 총액: 0원");
        } else {
        	// 빈 라벨 제거하고 스크롤 패널을 붙임
        	remove(emptyLabel);
        	add(scrollPane, BorderLayout.CENTER);
        	
            Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY);

            for (CartProduct cp : cartList) {
                Product p = cp.getProduct(); 

                JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                itemPanel.setBorder(border);
                itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                // 체크박스
                JCheckBox checkBox = new JCheckBox();
                checkBox.addItemListener(e -> {
                    if (checkBox.isSelected()) {
                        selectedItems.add(cp);
                    } else {
                        selectedItems.remove(cp);
                    }
                    updateTotalPrice();
                    
                    revalidate();
                    repaint();
                });

                // 정보 라벨
                String info = String.format("[%s] %s (%s) | 수량: %d개 | 가격: %,d원", 
                        p.getCategory(), p.getName(), p.getType(), cp.getQuantity(), cp.getProductTotalPrice());
                JLabel lblInfo = new JLabel(info);
                lblInfo.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

                itemPanel.add(checkBox);
                itemPanel.add(lblInfo);
                
                listPanel.add(itemPanel);
            }
        }
        
        updateTotalPrice();
        listPanel.revalidate();
        listPanel.repaint();
    }

    private void updateTotalPrice() {
        
    	int total = 0;
        for (CartProduct cp : selectedItems) {
            total += cp.getProductTotalPrice();
        }
        totalLabel.setText(String.format("선택 상품 총액: %,d원", total));
    }

    private void handlePay() {
    	int key = 0;
        if (selectedItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "결제할 상품을 선택해주세요.");
            return;
        }
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        
        inputPanel.add(new JLabel("이름:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("전화번호:"));
        inputPanel.add(phoneField);
        
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "결제 정보 입력", JOptionPane.OK_CANCEL_OPTION);
        
        if (result != JOptionPane.OK_OPTION) {
			return; 
		}
        
        if (nameField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "이름과 전화번호를 모두 입력해주세요.");
			return;
		}
        
        int confirm = JOptionPane.showConfirmDialog(this, "선택한 상품을 결제하시겠습니까?", "결제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

 	   	key = processPayment();
 	   	if(key == 0) {
 	   		JProgressBar progressBar = new JProgressBar();
	        progressBar.setIndeterminate(true);
	        
	        JOptionPane loadingPane = new JOptionPane("결제 승인 중... 잠시만기다려주세요.",
	        										  JOptionPane.INFORMATION_MESSAGE,
	        										  JOptionPane.DEFAULT_OPTION,
	        										  null,
	        										  new Object[]{},
	        										  null);
	        
	       JDialog loadingDialog = loadingPane.createDialog(this, "결제 진행중");
	       
	       Timer timer = new Timer(3000, e -> {
	    	   loadingDialog.dispose();
	       });
	       
	       timer.setRepeats(false);
	       timer.start();
	       
	       loadingDialog.setVisible(true);
 	   	}
 	   	else { 
	 	   	JOptionPane.showMessageDialog(
	 	           this,
	 	           "카트에 담긴 상품 수가 재고를 초과 했습니다. 다시 시도해주세요.",
	 	           "결제 실패",
	 	           JOptionPane.ERROR_MESSAGE
	 	       );
 	   	}
    }
        		
    private int processPayment() {
    	int key = 0;
    	List<CartProduct> successItems = new ArrayList<>();
    	
    	for (CartProduct cp : selectedItems) {
    		boolean success = Payment.payOneProduct(cp);
    		
    		if (success) {
				successItems.add(cp);
			}else {
				key++;
			}
    	}
    	if(key == 0) {
    	for (CartProduct cp : successItems) {
            CartManage.removeCart(cp, cp.getQuantity());
        }
        
        JOptionPane.showMessageDialog(this, "결제가 완료되었습니다!");
        
        refreshCart(); 
        mainFrame.showMainCard("HISTORY");
        return 0;
    	}else {
    		return 1;
    	}
    }
    
    private void handleDelete() {
        if (selectedItems.isEmpty()) return;
        
        for (CartProduct cp : selectedItems) {
            CartManage.removeCart(cp, cp.getQuantity());
        }
        refreshCart();
    }
    
    private void checkAll() {
    	
    }
}