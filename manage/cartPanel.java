package manage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import manage.cart;
import manage.Wine;

public class cartPanel extends JPanel{
	MainFrame mainFrame;
	JTextArea cartArea;
	
	
	public void CartPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setLayout(new BorderLayout());

		Image cartIcon = new ImageIcon("cartIcon");
		JLabel title = new JLabel("🛒 내 장바구니", SwingConstants.LEFT, cartIcon);
		title.setFont(new Font ("맑은 고딕", Font.BOLD, 20));
		add(title, BorderLayout.NORTH);
		
		cartArea = new JTextArea();
		cartArea.setEditable(false);
		add(new JScrollPane(cartArea),BorderLayout.WEST);
		
		JPanel btnPanel = new JPanel();
		JButton btnBack = new JButton("뒤로가기");
		JButton btnCheckOut = new JButton("결제하기");
		
		btnBack.addActionListener(new MyActionListener());		
		btnCheckOut.addActionListener(new MyActionListener());
		
		btnPanel.add(btnCheckOut);
		btnPanel.add(btnBack);
		add(btnPanel, BorderLayout.SOUTH);
		
	}
	
	private class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			if (b.getText().equals("뒤로가기")) {
				mainFrame.showCard("CART");
			}else if (b.getText().equals("결제하기")) {
				mainFrame.showCard("CHECKOUT");
			}
		}
		
	}
	
	/*public void showCARD(String name){
	 * if (name.equals("CART")){
	 * cartPa*/
	public void updateCartList() {
		cartArea.setText("");
		int total = 0;
		
		cartArea.append("상품명 | 가격\n");
		cartArea.append("_____________________________________\n");
		
		for (Wine w : cart.itemsInCart) {
			cartArea.append(w.getName() + " | " + w.getPrice() + "원 \n" );
			total += w.getPrice();
		}
		
		cartArea.append("_______________________________________\n");
		cartArea.append("총 합계: "+ total + "원");
	}
}

