package mallmain;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class Main extends JFrame {
	
	private CardLayout mainLayout;
    private JPanel mainContainer;
    
    private HomePanel homePanel;
    private WineShopPanel winePanel;
    private WineShopPanel beerPanel;
    private WineShopPanel whiskeyPanel;
    
    private JPanel cartPanel; 
    private JPanel hisPanel;
    private String lastCardName = "HOME";
    
    public Main() {
    	
    	// JFrame 기본 설정
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 시 프로그램 종료
        setTitle("Mall Main"); // 창 제목 설정
        setSize(1600, 900); // MainFrame 크기 1600 x 900
        setResizable(false);
        
        // CardLayout 설정
        mainLayout = new CardLayout();
        mainContainer = new JPanel(mainLayout);
        
        // 1. 패널들을 미리 생성 -> 생성자에 'this'를 넘기는 이유는 패널들이 메인 프레임에게 화면 전환 요청하기 위함
        homePanel = new HomePanel(this);
        winePanel = new WineShopPanel(this);
        beerPanel = new WineShopPanel(this);
        whiskeyPanel = new WineShopPanel(this);
        cartPanel = new manage.CartPanel(this);
        hisPanel = new manage.HisPanel(this);
        
        // 2. CardLayout에 패널들 등록 (이름표를 붙여줌)
        mainContainer.add(homePanel, "HOME");
        mainContainer.add(winePanel, "WINE");
        mainContainer.add(beerPanel, "BEER");
        mainContainer.add(whiskeyPanel, "WHISKEY");
        mainContainer.add(cartPanel,"CART"); 
        mainContainer.add(hisPanel,"HISTORY");
        add(mainContainer);
        setVisible(true);
    }

    // 화면 전환을 담당하는 메서드
    public void showMainCard(String cardName) {
    	// 마지막으로 본 카드 이름 저장 (CART와 HISTORY는 제외)
    	if (!cardName.equals("CART") && !cardName.equals("HISTORY")) {
			this.lastCardName = cardName;
		}
    	
    	// 화면 전환 전에 해당 패널의 새로고침 메서드 호출
    	if (cardName.equals("CART")) {
    		if (cartPanel instanceof manage.CartPanel) {
    			((manage.CartPanel)cartPanel).refreshCart();
    		}
    	}
    	
    	if (cardName.equals("HISTORY")) {
    		if (hisPanel instanceof manage.HisPanel) {
				((manage.HisPanel)hisPanel).refreshHistory();
			}
    	}
    	
        mainLayout.show(mainContainer, cardName);
    }
    
    public void goBack() {
		showMainCard(this.lastCardName);
	}
    
    public static void MyFont(JButton btn) {
    	btn.setFont(new Font("Noto Sans KR", Font.BOLD, 15 ));
    	//btn.setBorderPainted(false);      
    	btn.setContentAreaFilled(false);  
        btn.setFocusPainted(false);
    }
    public static Font getCustomFont(String fileName, float size) {
        try {
            String path = "data/" + fileName; 
            File fontFile = new File(path);
            
            //새로운 폰트 생성
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            
            // 크기 조절해서 반환
            return customFont.deriveFont(size);
            
        } catch (Exception e) {
            // 에러 나면 그냥 기본 폰트 반환해서 프로그램 안 꺼지게 함
            System.err.println("폰트 로드 실패: " + fileName);
            return new Font("Noto Sans KR", Font.BOLD, (int)size);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
        
    }

}