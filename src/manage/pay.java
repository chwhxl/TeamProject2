package manage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.Border;
import wineshop.*;
import wineshop.Wine;
import wineshop.WineList;
import mallmain.*;

// btn = 사진 버튼
// bttn = 이름 버튼
// lbl = 가격
// checkBox = 체크박스
// cartitem 로 된 친구들을 전부 진짜 카트 어레이 리스트와 연결시키면 결제는 끝
// JButton btn = new JButton(new ImageIcon("이미지경로")); 버튼에 이미지 넣는법

public class pay extends JPanel {

    private JPanel panel;                 // 메인 내부 패널 (static 제거)
    public static ArrayList<Integer> check = new ArrayList<>();
    public static ArrayList<Wine> cartitem = new ArrayList<>();

    public pay() {
    	
    	cartitem = WineList.wineList;
        // 이 pay 자체 레이아웃
        setLayout(new BorderLayout());

        // 내부 스크롤 대상 패널
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1)); // 세로 정렬

        // 버튼/아이템 패널 생성
        for (int i = 0; i < cartitem.size(); i++) {
            final int index = i;
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));

            Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY);

            // 셀 크기 (너무 작게 주면 잘림 → 조금 여유 있게 주거나 아예 안 준다)
            Dimension checkSize = new Dimension(60, 100);
            Dimension btnSize   = new Dimension(100, 100);
            // 라벨 폭이 너무 작으면 글자가 잘릴 수 있음 → 폭 키우거나 setPreferredSize 제거
            Dimension lblSize   = new Dimension(250, 100);

            // ---- CHECKBOX PANEL ----
            JCheckBox checkBox = new JCheckBox();
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (checkBox.isSelected()) {
                        System.out.println("체크됨: " + index + " 번 체크박스");
                        check.add(index);
                    } else {
                        System.out.println("해제됨: " + index + " 번 체크박스");
                        check.remove(Integer.valueOf(index));
                    }
                }
            });

            JPanel checkPanel = new JPanel(new GridBagLayout());
            checkPanel.setPreferredSize(checkSize);
            checkPanel.setBorder(border);
            checkPanel.add(checkBox);

            // ---- BUTTON PANEL 1 (사진) ----
            JButton btn = new JButton(new ImageIcon(cartitem.get(index).getImgPath()));
            btn.setFont(btn.getFont().deriveFont(8f));
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setContentAreaFilled(false);
            btn.setOpaque(false);

            JPanel btnPanel = new JPanel(new GridBagLayout());
            btnPanel.setPreferredSize(btnSize);
            btnPanel.setBorder(border);
            btnPanel.add(btn);

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("사진 버튼 클릭됨: " + cartitem.get(index).getName());
                    // 상세 페이지 열기
                }
            });

            // ---- BUTTON PANEL 2 (이름) ----
            JButton bttn = new JButton(cartitem.get(index).getName());
            bttn.setFont(bttn.getFont().deriveFont(11f));
            bttn.setBorderPainted(false);
            bttn.setFocusPainted(false);
            bttn.setContentAreaFilled(false);
            bttn.setOpaque(false);

            JPanel bttnPanel = new JPanel(new GridBagLayout());
            bttnPanel.setPreferredSize(btnSize);
            bttnPanel.setBorder(border);
            bttnPanel.add(bttn);

            bttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("이름 버튼 클릭됨: " + cartitem.get(index).getName());
                    // 상세 페이지 열기
                }
            });

            // ---- PRICE LABEL PANEL ----
            JLabel lbl = new JLabel(
                "가격: " + cartitem.get(index).getPrice() +
                "원, 갯수 : " + cartitem.get(index).getStock() + "개 "
            );
            JPanel lblPanel = new JPanel(new GridBagLayout());
            lblPanel.setPreferredSize(lblSize);
            lblPanel.setBorder(border);
            lblPanel.add(lbl);

            // 한 줄 구성
            itemPanel.add(checkPanel);
            itemPanel.add(btnPanel);
            itemPanel.add(bttnPanel);
            itemPanel.add(lblPanel);

            panel.add(itemPanel);
        }
        //JLabel totalPrice = new JLabel("총 가격  ") // 총 가격 표시 레이블
        // ---- 결제 버튼 ----
        JButton payy = new JButton("결제");
        panel.add(payy);
        payy.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	
                int result = JOptionPane.showInternalConfirmDialog(pay.this,"결제를 진행하시겠습니까?" ,"Confirm",JOptionPane.YES_NO_OPTION );
                if(result == JOptionPane.YES_OPTION) {
                	
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

                	int result = JOptionPane.showConfirmDialog(
                	        pay.this, 
                	        inputPanel, 
                	        "결제 정보 입력", 
                	        JOptionPane.OK_CANCEL_OPTION
                	);

                	if (result == JOptionPane.OK_OPTION) {
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
                	            
                	            // 3. 결제 로직 처리 (로딩이 끝난 후 실행)
                	            processPayment(); 
                	        }
                	    });
                	    
                	    timer.setRepeats(false); // 한 번만 실행
                	    timer.start(); // 타이머 시작
                	    
                	    loadingDialog.setVisible(true); // 로딩 창 보여주기 (여기서 코드 대기)
                	}
                	//-----여기까지 수정
                	int tp=0;
                    for (int j = check.size()-1; j > -1; j--) {
                    	tp += payment.payments(check.get(j));
                        if (cartitem.get(j).getStock() == 0) {
                            cartitem.remove(j);
                        }
                    }
                    check.clear();
                    JOptionPane.showMessageDialog(pay.this, tp + " 원 결제가 완료되었습니다.");
                    MallMain.Panel.removeAll();
                    MallMain.Panel.add(MallMain.mainPanel);	 //작동 안함
                    MallMain.Panel.revalidate();
                    MallMain.Panel.repaint();
                  
                }else if(result == JOptionPane.NO_OPTION) {
                	JOptionPane.showMessageDialog(pay.this, "결제 취소.");
                }else{
                    JOptionPane.showMessageDialog(pay.this, "결제 취소.");
                }
            }
        });

        // ---- 구매내역 버튼 ----
        JButton hist = new JButton("구매내역");
        panel.add(hist);
        hist.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	//메인에서 화면전환 함수 넣기
            	MallMain.Panel.removeAll();
                //MallMain.Panel.add(history.hispanel);	 //작동 안함
                MallMain.Panel.revalidate();
                MallMain.Panel.repaint();
            	//main_shop.setContenpanel(main_shop.hisPanel);
            }
        });

        // ---- Scroll 가능하게 ----
        JScrollPane scroll = new JScrollPane(
                panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        // 이 pay 패널의 중앙에 스크롤 붙이기
        add(scroll, BorderLayout.CENTER);
    }

    // 프레임에서 pack() 쓸 때 참고할 기본 크기
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 700);
    }
}
