package User;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.Border;

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

        // 한 번 들어올 때마다 cartitem 채우기 (중복 방지하려면 clear() 먼저)
        cartitem.clear();
       // for (int i = 0; i < cart.list.length; i++) {
         //   cartitem.add(cart.list[i]);
        //}

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
            JButton btn = new JButton(new ImageIcon("C:\\Users\\Administrator\\Desktop\\박준혁_사인.png"));
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

        // ---- 결제 버튼 ----
        JButton payy = new JButton("결제");
        panel.add(payy);
        payy.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < check.size(); i++) {
                    payment.payments(check.get(i));
                }
                for (int i = cartitem.size() - 1; i >= 0; i--) {
                    if (cartitem.get(i).getStock() == 0) {
                        cartitem.remove(i);
                    }
                }
                check.clear();
                JOptionPane.showMessageDialog(pay.this, "결제가 완료되었습니다.");
                // 필요하면 여기서 다시 panel 리빌드(reload) 로직 추가
            }
        });

        // ---- 구매내역 버튼 ----
        JButton hist = new JButton("구매내역");
        panel.add(hist);
        hist.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	//메인에서 화면전환 함수 넣기
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
