package manage;

import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Gmailsender {
	
		static void SendGmail(String customer_email, String customer_name) {
        String host = "smtp.gmail.com";
        final String user = "ganadi.haerong@gmail.com";
        final String password = "="; // 공백 없이 입력

        String to = customer_email; // 받는 사람 이메일

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587"); // TLS 포트
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS 필수 사용
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // SSL 신뢰 설정
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            // 이메일 내용 작성
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("["+customer_name+ "님] 가나디 헤롱샵 구매 내역"); 

            message.setText(emailContent());

            // 전송
            Transport.send(message);
            System.out.println("성공! 이메일을 보냈습니다.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("실패: " + e.getMessage());
        	}
		}
		
		//이메일 본문 내용 정리
        public static String emailContent() {
            List<HistoryProduct> list = HistoryManage.getHistoryList();
      
            StringBuilder sb = new StringBuilder();
            int Total = 0;

            sb.append("가나디 헤롱샵을 이용해주셔서 감사합니다 🎀 \n\n");
            sb.append("주문 정보 : \n");

            for (HistoryProduct hp : list) {
                int sum = hp.getPrice() * hp.getQuantity();
                Total += sum;
                sb.append(String.format("- %s (%,d원) x %d개 : %,d원\n", 
                          hp.getName(), hp.getPrice(), hp.getQuantity(), sum));
            }

            sb.append(String.format(">> 총 결제 금액 : %,d원\n", Total));
            sb.append("\n 배송정보 확인하기: https://jumisong02.github.io/ganadi-haerongshop/ganadi_TEST.html");
            
            return sb.toString();
        
    
    }
}
