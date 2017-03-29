package com.stravaapi.komwatcher;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceClassPathResolver;
import org.apache.log4j.Logger;

import com.stravaapi.komwatcher.config.EmailConfig;

public class EmailService {
	
	final static Logger logger = Logger.getLogger(EmailService.class);
	
	public EmailService() {}
	
	public void sendEmail(String emailSubject, String renderedHtml, String recipientAddress) {	    
		
		try {
			
			logger.info("Sending email to " + recipientAddress);
			ImageHtmlEmail email = new ImageHtmlEmail();
			DataSourceResolver dsr =  new DataSourceClassPathResolver();
			email.setDataSourceResolver(dsr);
			email.setHostName(EmailConfig.HOST_NAME);
			email.setSmtpPort(EmailConfig.SMTP_PORT);
			email.setSSLOnConnect(EmailConfig.SSL_ON_CONNECT);
			email.setAuthenticator(new DefaultAuthenticator(EmailConfig.AUTH_USERNAME, EmailConfig.AUTH_PASSWORD));
			email.setFrom(EmailConfig.SENDER_ADDRESS, EmailConfig.SENDER_NAME);
			email.setSubject(emailSubject);
			email.setHtmlMsg(renderedHtml);
			email.addTo(recipientAddress);
			email.send();
		} catch (EmailException e) {
			// TODO Add debug logging
			e.printStackTrace();
		}
		
	}
}
