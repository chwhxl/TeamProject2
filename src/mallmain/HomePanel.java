package mallmain;
 
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class HomePanel extends JPanel{
	
	private Image backgroundImage;
	
	public HomePanel(Main mainFrame) {
        // HomePanel 레이아웃 및 배경색 설정
		setLayout(new BorderLayout()); 
		setBackground(Color.WHITE);
		
        ImageIcon titleIcon = new ImageIcon("data/mallmain_icon/mainpage/hr.png");
        backgroundImage = titleIcon.getImage();
                     
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);

       
        String[] shopNames = {"wine", "beer", "liquor"};
        
        //버튼 클릭하는 범위를 줄여줌 
        for (String name : shopNames) {
        	JButton shopBtn = new JButton() {
        	    @Override
        	    public boolean contains(int x, int y) {
        	        int w = getWidth();
        	        int h = getHeight();
        	        
        	        int marginX = w / 4; 
        	       
        	        if (x < marginX || x > w - marginX) {
        	            return false; 
        	        }
        	        
        	        return super.contains(x, y); 
        	    }
        	};

            String imgPath = "data/mallmain_icon/mainpage/" + name + ".png";
            ImageIcon icon = new ImageIcon(imgPath);
            Image image = icon.getImage();

            if (image.getWidth(null) != -1) {

                Image scaledImg = image.getScaledInstance(410, -1, Image.SCALE_SMOOTH);
                ImageIcon realIcon = new ImageIcon(scaledImg);
                

                // 평소에는 이미지 안보이게 투명처리
                BufferedImage transparentImg = new BufferedImage(
                        realIcon.getIconWidth(), realIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                ImageIcon transparentIcon = new ImageIcon(transparentImg);

                shopBtn.setIcon(transparentIcon);        // 평소: 투명 이미지 (안 보임)
                shopBtn.setRolloverIcon(realIcon);       // 마우스 올림: 이미지 보임
                shopBtn.setPressedIcon(realIcon);        // 클릭 중: 이미지 유지
                
                // 프레임 크기를 받아와서 상대적 위치로 버튼 위치 설정
                int frameWidth = mainFrame.getWidth();
                int frameHeight = mainFrame.getHeight();
                
                int width = realIcon.getIconWidth();
                int height = realIcon.getIconHeight();
                int mywidth = (int)(width/2);
                int myheight = (int)(height/2);
                
                if (name.equals("wine")) {
                	int x = (int)(frameWidth * 0.405) - mywidth;
                    int y = (int)(frameHeight * 0.48) - myheight;
                    shopBtn.setBounds(x,y, width, height);
                } else if (name.equals("liquor")) {
                	int x = (int)(frameWidth * 0.55) - mywidth;
                    int y = (int)(frameHeight * 0.5) - myheight;
                    shopBtn.setBounds(x,y, width, height);
                } else if (name.equals("beer")) {
                	int x = (int)(frameWidth * 0.64) - mywidth;
                    int y = (int)(frameHeight * 0.60) - myheight;
                    shopBtn.setBounds(x,y, width, height);                }
                
            } else {
                System.out.println("이미지를 찾을 수 없습니다 " + imgPath);
            }

            // 스타일 제거
            shopBtn.setBorderPainted(false);
            shopBtn.setContentAreaFilled(false);
            shopBtn.setFocusPainted(false);
            shopBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // 클릭 이벤트
            shopBtn.addActionListener(e -> {

                if (name.equals("wine")) mainFrame.showMainCard("WINE");
                else if (name.equals("beer")) mainFrame.showMainCard("BEER");
                else if (name.equals("liquor")) mainFrame.showMainCard("LIQUOR");

            });

            buttonPanel.add(shopBtn);
        }
        
        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
