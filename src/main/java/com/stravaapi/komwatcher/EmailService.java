package com.stravaapi.komwatcher;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceClassPathResolver;
import org.apache.log4j.Logger;

public class EmailService {
	
	final static Logger logger = Logger.getLogger(EmailService.class);
	
	public EmailService() {}
	
	public void sendEmail(String emailSubject, String renderedHtml, String recipientAddress) {	    
		
		try {
			
			logger.info("Sending email to " + recipientAddress);
			ImageHtmlEmail email = new ImageHtmlEmail();
			DataSourceResolver dsr =  new DataSourceClassPathResolver();
			email.setDataSourceResolver(dsr);
			email.setHostName(SystemProperties.getPropertyAsString(PropertiesConstants.HOST_NAME));
			email.setSmtpPort(SystemProperties.getPropertyAsInteger(PropertiesConstants.SMTP_PORT));
			email.setSSLOnConnect(SystemProperties.getPropertyAsBoolean(PropertiesConstants.SSL_ON_CONNECT));
			email.setAuthenticator(new DefaultAuthenticator(SystemProperties.getPropertyAsString(PropertiesConstants.AUTH_USERNAME), 
					SystemProperties.getPropertyAsString(PropertiesConstants.AUTH_PASSWORD)));
			email.setFrom(SystemProperties.getPropertyAsString(PropertiesConstants.SENDER_ADDRESS), 
					SystemProperties.getPropertyAsString(PropertiesConstants.SENDER_NAME));
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
