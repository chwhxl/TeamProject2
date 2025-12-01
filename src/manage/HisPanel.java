package manage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.List;
import mallmain.Main; // 메인 프레임 import 확인 필요

public class HisPanel extends JPanel {

    private JPanel hispanel; // 내용을 담을 패널
    private Main mainFrame;  // 화면 전환용 메인 프레임 참조

    public HisPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // 1. 상단 타이틀 (선택사항)
        JLabel titleLabel = new JLabel("구매 내역 (영수증)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 2. 중앙 리스트 패널 (스크롤 안에 들어갈 내용물)
        hispanel = new JPanel();
        // 세로로 쭉 쌓이는 레이아웃 (BoxLayout Y_AXIS가 더 자연스러움)
        hispanel.setLayout(new BoxLayout(hispanel, BoxLayout.Y_AXIS)); 
        
        JScrollPane scroll = new JScrollPane(hispanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 조절
        add(scroll, BorderLayout.CENTER);

        // 3. 하단 버튼 패널
        JPanel bottomPanel = new JPanel();
        JButton btnHome = new JButton("메인으로 돌아가기");
        
        btnHome.addActionListener(e -> mainFrame.showMainCard("HOME"));
        
        bottomPanel.add(btnHome);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // 초기화 시 한 번 그려줌 (비어있겠지만)
        refreshHistory();
    }

    // 화면을 갱신하는 메서드 (외부에서 호출 가능)
    public void refreshHistory() {
        hispanel.removeAll(); // 기존 목록 싹 지우기

        List<HistoryProduct> list = HistoryManage.getHistoryList(); // 매니저에서 데이터 가져오기

        // 데이터가 없으면 안내 문구 표시
        if (list.isEmpty()) {
            JLabel emptyLabel = new JLabel("구매 내역이 없습니다.");
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            hispanel.add(Box.createVerticalStrut(20)); // 여백
            hispanel.add(emptyLabel);
        } else {
            // 리스트 루프 돌면서 아이템 패널 생성
            for (HistoryProduct product : list) {
                hispanel.add(createItemPanel(product));
                hispanel.add(Box.createVerticalStrut(10)); // 아이템 사이 간격
            }
        }

        hispanel.revalidate(); // 레이아웃 다시 계산
        hispanel.repaint();    // 화면 다시 그리기
    }

    // 아이템 하나를 만드는 메서드
    private JPanel createItemPanel(HistoryProduct product) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // 밑줄
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // 가로로 꽉 차게
        panel.setBackground(Color.WHITE);

        // 1. 상품명
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        nameLabel.setPreferredSize(new Dimension(200, 50));
        
        // 2. 수량 & 가격 정보
        String infoText = String.format("%d개 | %,d원", product.getQuantity(), product.getProductTotalPrice());
        JLabel infoLabel = new JLabel(infoText);
        infoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        panel.add(nameLabel);
        panel.add(infoLabel);

        return panel;
    }
}