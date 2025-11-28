package manage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.Border;

import MallMain.MallMain;
import wineshop.Wine;

public class history extends JPanel {
    public static ArrayList<Wine> historyitem = new ArrayList<>();
    public static JPanel hispanel = MallMain.hisPanel;   // 스크롤 내부 panel

    public history() {

        setLayout(new BorderLayout());  // 패널의 기본 레이아웃

        hispanel = new JPanel();
        hispanel.setLayout(new GridLayout(0, 1));

        // 아이템 리스트 표시
        for (int i = 0; i < historyitem.size(); i++) {
            final int index = i;

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));

            Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY);

            Dimension btnSize = new Dimension(100, 100);   // 전각 숫자 제거
            Dimension lblSize = new Dimension(200, 100);

            // 이미지 버튼
            JButton btn = new JButton(new ImageIcon("C:\\Users\\Administrator\\Desktop\\박준혁_사인.png"));
            btn.setBorderPainted(false); //버튼 모양 숨기기
            btn.setFocusPainted(false);
            btn.setContentAreaFilled(false);

            JPanel btnPanel = new JPanel(new GridBagLayout());
            btnPanel.setPreferredSize(btnSize);
            btnPanel.setBorder(border);
            btnPanel.add(btn);

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("이미지 클릭: " + historyitem.get(index).getName());
                }
            });

            // 이름 버튼
            JButton bttn = new JButton(historyitem.get(index).getName());
            bttn.setBorderPainted(false);
            bttn.setContentAreaFilled(false);
            bttn.setFocusPainted(false);

            JPanel bttnPanel = new JPanel(new GridBagLayout());
            bttnPanel.setPreferredSize(btnSize);
            bttnPanel.setBorder(border);
            bttnPanel.add(bttn);

            bttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("이름 클릭: " + historyitem.get(index).getName());
                }
            });

            // 가격 라벨
            JLabel lbl = new JLabel("가격: " + historyitem.get(index).getPrice() + "원");
            JPanel lblPanel = new JPanel(new GridBagLayout());
            lblPanel.setPreferredSize(lblSize);
            lblPanel.setBorder(border);
            lblPanel.add(lbl);

            // 하나의 itemPanel 구성
            itemPanel.add(btnPanel);
            itemPanel.add(bttnPanel);
            itemPanel.add(lblPanel);

            hispanel.add(itemPanel);
        }

        // 이전 버튼
        JButton back = new JButton("이전");
        hispanel.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
				//메인에서 화면전환 함수 넣기
            	MallMain.Panel.removeAll();
                MallMain.Panel.add(MallMain.PayPanel);	 //작동 안함
                MallMain.Panel.revalidate();
                MallMain.Panel.repaint();
            }
        });

        // 스크롤 포함
        JScrollPane scroll = new JScrollPane(
            hispanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scroll, BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 700);
    }
}
