package mallmain;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import com.formdev.flatlaf.FlatLightLaf;

public class Main extends JFrame {
	
	private CardLayout mainLayout;
    private JPanel mainContainer;
    
    private HomePanel homePanel;
    private WineShopPanel winePanel;
    private BeerShopPanel beerPanel;
    private LiquorShopPanel liquorPanel;
    
    private JPanel cartPanel; 
    private JPanel hisPanel;
    private String lastCardName = "HOME";
    
    public Main() {
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setTitle("Mall Main"); 
        setSize(1440, 810); // 16:9
        setResizable(false);
        
        mainLayout = new CardLayout();
        mainContainer = new JPanel(mainLayout);
        
        homePanel = new HomePanel(this);
        winePanel = new WineShopPanel(this);
        beerPanel = new BeerShopPanel(this);
        liquorPanel = new LiquorShopPanel(this);
        cartPanel = new manage.CartPanel(this);
        hisPanel = new manage.HisPanel(this);
        
        // 각각 쇼핑몰 이름 붙여주기
        mainContainer.add(homePanel, "HOME");
        mainContainer.add(winePanel, "WINE");
        mainContainer.add(beerPanel, "BEER");
        mainContainer.add(liquorPanel, "LIQUOR");
        mainContainer.add(cartPanel,"CART"); 
        mainContainer.add(hisPanel,"HISTORY");
        add(mainContainer);
        setVisible(true);
    }

    // 화면 전환을 담당하는 메서드
    public void showMainCard(String cardName) {
    	// 화면 재고 새로고침
    	if (cardName.equals("WINE")) {
    		this.lastCardName = cardName;
    		winePanel.refreshShop();
    	}
    	
    	if (cardName.equals("BEER")) {
    		this.lastCardName = cardName;
			beerPanel.refreshShop();
		}
    	
    	if (cardName.equals("LIQUOR")) {
    		this.lastCardName = cardName;
    		liquorPanel.refreshShop();
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
    	btn.setFont(new Font("Noto Sans KR", Font.PLAIN, 13));
        btn.setFocusPainted(false);
    }
    
    public static Font getCustomFont(String fileName, float size) {
        try {
            String path = "data/" + fileName; 
            File fontFile = new File(path);
            
            //새로운 폰트 생성
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            
            // 크기 조절해서 반환
            return customFont.deriveFont(Font.PLAIN, size);
            
        } catch (Exception e) {
            // 에러 나면 그냥 기본 폰트 반환해서 프로그램 안 꺼지게 함
            System.err.println("폰트 로드 실패: " + fileName);
            return new Font("Noto Sans KR", Font.PLAIN, (int)size);
        }
    }
    
    public static void resetScroll(JScrollPane scrollPane) {
        if (scrollPane == null) return; // 안전장치

        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
    
    public static void main(String[] args) {
    	FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> new Main());
        
    }

}