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
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.log4j.Logger;

import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.SmtpProperties;

public interface SmtpHandler {	
	boolean sendEmail(EmailRequest request, SmtpProperties smtpProps);

	void sendEmail(SmtpProperties smtpProps, Session session, MimeMessage msg) throws MessagingException;

	MimeMessage createMimeMessage(EmailRequest request, Session session, SmtpProperties smtpProps);

	Properties createProperties(SmtpProperties smtpProps);
}
