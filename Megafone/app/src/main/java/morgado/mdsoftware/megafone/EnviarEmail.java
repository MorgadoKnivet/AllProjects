package morgado.mdsoftware.megafone;

import java.util.*;




public class EnviarEmail {

	 private static String USER_NAME = "canivete.juridico@gmail.com";  
	 private static String PASSWORD = "legalhack";
	 private static String RECIPIENT = "canivete.juridico@gmail.com";
	 private static String[] to = { RECIPIENT }; 
	 private static String subject = "Exemplo assunto";
     private static String body = "Exemplo corpo do email";
     
     /*
     

     public static void main(String[] args) {
         setEmail("canivete.juridico@gmail.com");
     }
     

     
	public static void sendEmail(String emailDestinatario) {
		
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		 props.put("mail.smtp.starttls.enable", "true");
		 props.put("mail.smtp.host", host);
		 props.put("mail.smtp.user", USER_NAME);
		 props.put("mail.smtp.password", PASSWORD);
		 props.put("mail.smtp.port", "587");
	     props.put("mail.smtp.auth", "true");
		
	     Session session = Session.getDefaultInstance(props);
	     MimeMessage message = new MimeMessage(session);
	     
	     try {
	    	 message.setFrom(new InternetAddress(USER_NAME));
	    	 InternetAddress[] toAddress = new InternetAddress[to.length];
	    	 
	    	 for( int i = 0; i < to.length; i++ ) {
	                toAddress[i] = new InternetAddress(to[i]);
	            }
	    	 
	    	 for( int i = 0; i < toAddress.length; i++) {
	    		 message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	    		 
	    	 }
	    	 
	    	 message.setSubject(subject);
	    	 message.setText(body);
	    	 Transport transport = session.getTransport("smtp");
	            transport.connect(host,USER_NAME, PASSWORD);
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	    	 
	    	 
	    	 
	     }catch (AddressException ae) {
	            ae.printStackTrace();
	        }
	        catch (MessagingException me) {
	            me.printStackTrace();
	        }
		
		
	}
	
	
	
	public static void setEmail(String email) {
		sendEmail(email);
		
	}
}
   */}