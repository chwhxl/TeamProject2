package mallmain;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Main extends JFrame {
	
	private CardLayout mainLayout;
    private JPanel mainContainer;
    
    private HomePanel homePanel;
    private ShopPanel winePanel;
    private ShopPanel beerPanel;
    private ShopPanel whiskeyPanel;
    
    public Main() {
    	// JFrame 기본 설정
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 시 프로그램 종료
        setTitle("Mall Main"); // 창 제목 설정
        setSize(1600, 900); // MainFrame 크기 1600 x 900
        
        // CardLayout 설정
        mainLayout = new CardLayout();
        mainContainer = new JPanel(mainLayout);

        // 1. 패널들을 미리 생성 -> 생성자에 'this'를 넘기는 이유는 패널들이 메인 프레임에게 화면 전환 요청하기 위함
        homePanel = new HomePanel(this);
        winePanel = new ShopPanel(this, "와인 상점", new Color(255, 240, 240)); // 연한 빨강 배경
        beerPanel = new ShopPanel(this, "맥주 상점", new Color(255, 255, 224)); // 연한 노랑 배경
        whiskeyPanel = new ShopPanel(this, "위스키 상점", new Color(240, 248, 255)); // 연한 파랑 배경

        // 2. CardLayout에 패널들 등록 (이름표를 붙여줌)
        mainContainer.add(homePanel, "HOME");
        mainContainer.add(winePanel, "WINE");
        mainContainer.add(beerPanel, "BEER");
        mainContainer.add(whiskeyPanel, "WHISKEY");

        add(mainContainer);
        setVisible(true);
    }

    // 화면 전환을 담당하는 메서드
    public void showMainCard(String cardName) {
        mainLayout.show(mainContainer, cardName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }

}
