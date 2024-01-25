package com.Transaction.transaction;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.*;
import java.util.*;

@SpringBootApplication
@EnableTransactionManagement
public class TransactionApplication extends Authenticator {
//	static String from="mukeshsilwal5@gmail.com";
//	static String password="$@31Brother";
	public static void main(String[] args) throws MessagingException {
		SpringApplication.run(TransactionApplication.class, args);
//		Properties properties=System.getProperties();
//		properties.put("mail.smtp.auth","true");
//		properties.put("mail.smtp.starttls.enable","true");
//		properties.put("mail.smtp.host","smtp.gmail.com");
//		properties.put("mail.smtp.port","587");
//		properties.put("mail.smtp.user",from);
//		properties.put("mail.smtp.password",password);
//		Session session=Session.getDefaultInstance(properties);
//		MimeMessage mimeMessage=new MimeMessage(session);
//		mimeMessage.setFrom(new InternetAddress("mukeshsilwal5@gmail.com"));
//		InternetAddress too=new InternetAddress("kusumchhetri607@gmail.com");
//		mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(String.valueOf(too)));
//		mimeMessage.setSubject("Test Java Mail");
//		mimeMessage.setText("Hello from mukesh mail");
//		Transport transport=session.getTransport("smtp");
//		transport.connect("smtp.gmail.com",from, String.valueOf(too));
//		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
//		System.out.println("message sent successfully ......");


	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
