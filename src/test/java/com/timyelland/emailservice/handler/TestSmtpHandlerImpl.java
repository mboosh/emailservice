package com.timyelland.emailservice.handler;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.SmtpProperties;

public class TestSmtpHandlerImpl implements SmtpHandler {

	@Override
	public boolean sendEmail(EmailRequest request, SmtpProperties smtpProps) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendEmail(SmtpProperties smtpProps, Session session, MimeMessage msg) throws MessagingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MimeMessage createMimeMessage(EmailRequest request, Session session, SmtpProperties smtpProps) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties createProperties(SmtpProperties smtpProps) {
		// TODO Auto-generated method stub
		return null;
	}



}
