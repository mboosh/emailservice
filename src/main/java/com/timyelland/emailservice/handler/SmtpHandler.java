package com.timyelland.emailservice.handler;

import java.util.Objects;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.timyelland.emailservice.data.EmailProviderSmtpProperties;
import com.timyelland.emailservice.data.EmailRequest;

public class SmtpHandler {
	public static boolean sendEmail(EmailRequest request, EmailProviderSmtpProperties smtpProps) {
		final Properties props = createProperties(smtpProps);
		final Session session = Session.getDefaultInstance(props);
		MimeMessage msg = createMimeMessage(request, session, smtpProps);
		if (Objects.isNull(msg)) {
			return false;
		}
		
		try {
			sendEmail(smtpProps, session, msg);			
		} catch (final Exception exception) {
			return false;
		}
		return true;
	}

	private static void sendEmail(EmailProviderSmtpProperties smtpProps, final Session session, MimeMessage msg)
			throws Exception {
		final Transport transport = session.getTransport();
		transport.connect(smtpProps.getHost(), smtpProps.getUserName(), smtpProps.getPassword());
		transport.sendMessage(msg, msg.getAllRecipients());		
		transport.close();
	}

	private static MimeMessage createMimeMessage(EmailRequest request, final Session session, EmailProviderSmtpProperties smtpProps) {
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(smtpProps.getFromEmail()));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(request.getToEmail()));
			msg.setSubject(request.getSubject());
			msg.setContent(request.getContent(), "text/plain");
			return msg;
		} catch (final Exception e) {
			return null;
		}
	}

	private static Properties createProperties(EmailProviderSmtpProperties smtpProps) {
		final Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.port", smtpProps.getPort());
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");
		return props;
	}
}
