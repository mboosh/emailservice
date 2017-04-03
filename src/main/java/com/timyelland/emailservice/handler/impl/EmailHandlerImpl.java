package com.timyelland.emailservice.handler.impl;

import java.util.Objects;

import org.apache.log4j.Logger;

import com.timyelland.emailservice.constants.ResponseMessages;
import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;
import com.timyelland.emailservice.data.SmtpProperties;
import com.timyelland.emailservice.handler.EmailHandler;
import com.timyelland.emailservice.handler.SmtpHandler;

/*
 * EmailHandler which contains the next email handler to pass on the responsibility
 * when this EmailHandler is unable to send the email. 
 */
public class EmailHandlerImpl implements EmailHandler {
	final static Logger logger = Logger.getLogger(EmailHandlerImpl.class);
	
	private EmailHandler nextEmailHandler;
	private SmtpProperties properties;
	private SmtpHandler smtpHandler;
	

	public EmailHandlerImpl(final SmtpProperties properties, final SmtpHandler smtpHandler) {
		this.properties = properties;
		this.smtpHandler = smtpHandler;
	}	

	@Override
	public void nextHandler(final EmailHandler nextEmailHandler) {
		logger.debug("Method: nextHandler");
		this.nextEmailHandler = nextEmailHandler;
	}

	@Override
	public boolean handleRequest(final EmailRequest request, final EmailResponse response) {
		logger.debug("Method: handleRequest");
		final boolean successful = sendEmail(request, response);
		logger.debug("Sending email was: " + successful);
		if (successful) {
			setEmailSentSuccessfullyResponse(response);
		} else {
			if (Objects.isNull(nextEmailHandler)) {
				setUnableToSendEmailResponse(response);
			} else {
				return nextEmailHandler.handleRequest(request, response);
			}
		}
		return successful;
	}
	
	private void setEmailSentSuccessfullyResponse(final EmailResponse response) {
		logger.debug("Method: setEmailSentSuccessfullyResponse");
		response.setMessage(ResponseMessages.EMAIL_SENT_SUCCESSFULLY, null);		
	}

	private void setUnableToSendEmailResponse(final EmailResponse response) {
		logger.debug("Method: setUnableToSendEmailResponse");
		response.setMessage(ResponseMessages.UNABLE_TO_SEND_EMAIL, null);
	}

	private boolean sendEmail(final EmailRequest request, final EmailResponse response) {
		logger.debug("Method: sendEmail: " + request.getToEmail());
		return smtpHandler.sendEmail(request, response, properties);
	}	
}
