package net.developia.mini1st.service;
import javax.mail.internet.MimeMessage;
public interface EmailSendService {
	public MimeMessage createMessage(String to, Integer pw) throws Exception;
	public String sendSimpleMessage(String to, Integer pw)throws Exception;
}
