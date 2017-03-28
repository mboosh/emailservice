package com.timyelland.emailservice.handler.impl;

import java.util.Objects;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.log4j.Logger;

import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.SmtpProperties;
import com.timyelland.emailservice.handler.EmailManager;
import com.timyelland.emailservice.handler.SmtpHandler;

public class LiveSmtpHandler implements SmtpHandler {
	final static Logger logger = Logger.getLogger(LiveSmtpHandler.class);
	
	public boolean sendEmail(EmailRequest request, SmtpProperties smtpProps) {
		logger.debug("Method: sendEmail: " + request.getToEmail());
		final Properties props = createProperties(smtpProps);
		final Session session = Session.getDefaultInstance(props);
		MimeMessage msg = createMimeMessage(request, session, smtpProps);
		if (Objects.isNull(msg)) {
			logger.error("Unable to create MimeMessage");
			return false;
		}
		
		try {
			sendEmail(smtpProps, session, msg);			
		} catch (final Exception ex) {			
			logger.error("Unable to sendEmail", ex);
			return false;
		}
		return true;
	}

	public void sendEmail(SmtpProperties smtpProps, final Session session, MimeMessage msg) throws MessagingException {
		logger.debug("Method: sendEmail(smtpProps, session, msg");
		final Transport transport = session.getTransport();
		transport.connect(smtpProps.getHost(), smtpProps.getUserName(), smtpProps.getPassword());
		transport.sendMessage(msg, msg.getAllRecipients());		
		transport.close();
	}

	public MimeMessage createMimeMessage(EmailRequest request, final Session session, SmtpProperties smtpProps) {
		logger.debug("Method: createMimeMessage");
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(smtpProps.getFromEmail()));
			msg.setRecipient(RecipientType.TO, new InternetAddress(request.getToEmail()));			
			if (Objects.nonNull(request.getCcEmail()) && !request.getCcEmail().isEmpty()) {
				msg.setRecipient(RecipientType.CC, new InternetAddress(request.getCcEmail()));
			}
			msg.setSubject(request.getSubject());
			msg.setContent(request.getContent(), "text/plain");
			return msg;
		} catch (final Exception ex) {
			logger.error("Exception creatingMimeMessage", ex);
			return null;
		}
	}

	public Properties createProperties(SmtpProperties smtpProps) {
		logger.debug("Method: createProperties");
		final Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.port", smtpProps.getPort());
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");
		return props;
	}
}
