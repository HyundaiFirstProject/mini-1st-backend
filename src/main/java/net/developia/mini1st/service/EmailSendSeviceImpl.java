package net.developia.mini1st.service;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendSeviceImpl implements EmailSendService {
	 
	@Autowired
	JavaMailSender emailSender;

	    public MimeMessage createMessage(String to, Integer pw) throws Exception{
	        System.out.println("������ ��� : "+ to);
	        System.out.println("���� ��ȣ : "+ pw);
	        MimeMessage  message = emailSender.createMimeMessage();

	        message.addRecipients(RecipientType.TO, to);//������ ���
	        message.setSubject("�̸��� ����");//����

	        String msgg="";
	        msgg+= "<div align='center' style='margin:30px; color:black;'>";
	        msgg+= "<h2 align='left' style='margin-left:10%;'>��������</h2>";
	        msgg+= "<hr style='background-color:#000000; height:2px; width:80%;'/>";
	        msgg+= "<br>";
	        msgg+= "<br>";
	        msgg+= "������ȣ �߼� �����Դϴ�.\n";
	        msgg+= "<br>";
	        msgg+= "<br>";
	        msgg+= "�Ʒ��� ������ȣ�� ����Ͽ� �̸��� �ּ� ������ �Ϸ��ϸ� ���� �ܰ�� ������ �����մϴ�.\n";
	        msgg+= "<br>";
	        msgg+= "<br>";
	        msgg+= "<br>";
	        msgg+= "<div align='center' style='border:none; height: 50px;\n" +
	                "   line-height: 50px; background-color:lightgrey; width:80%'>";
	        msgg+= "<div style='font-size:130%;'>";
	        msgg+= "<strong>"+pw+"</strong><div><br/></div>";

	        message.setText(msgg, "utf-8", "html");//����
	        message.setFrom(new InternetAddress("kimeunseo0508@gmail.com","����IT&E 1�� ������Ʈ"));//������ ���

	        return message;
	    }


	    public String sendSimpleMessage(String to, Integer pw) throws Exception {
	        // TODO Auto-generated method stub
	        MimeMessage message = createMessage(to, pw);
	        try{//����ó��
	            emailSender.send(message);
	        }catch(MailException es){
	            es.printStackTrace();
	            throw new IllegalArgumentException();
	        }
	        return pw.toString();
	    }



}
