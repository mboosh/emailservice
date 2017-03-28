package com.timyelland.emailservice.handler;

import java.util.Objects;

import org.apache.log4j.Logger;

import com.timyelland.emailservice.constants.ResponseMessages;
import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;
import com.timyelland.emailservice.data.SmtpProperties;

public class EmailHandlerImpl implements EmailHandler {
	final static Logger logger = Logger.getLogger(EmailHandlerImpl.class);
	
	private EmailHandler nextEmailHandler;
	private SmtpProperties properties;
	private SmtpHandler smtpHandler;
	

	public EmailHandlerImpl(SmtpProperties properties) {
		this.properties = properties;
	}	

	@Override
	public void nextHandler(EmailHandler nextEmailHandler, final SmtpHandler smtpHandler) {
		logger.debug("Method: nextHandler");
		this.nextEmailHandler = nextEmailHandler;
		this.smtpHandler = smtpHandler;
	}

	@Override
	public EmailResponse handleRequest(EmailRequest request) {
		logger.debug("Method: handleRequest");
		final boolean successful = sendEmail(request);
		logger.debug("Sending email was: " + successful);
		return successful ? returnEmailSentSuccessfullyResponse() : Objects.isNull(nextEmailHandler) ? returnUnableToSendEmailResponse()  : nextEmailHandler.handleRequest(request);
	}
	
	private EmailResponse returnEmailSentSuccessfullyResponse() {
		logger.debug("Method: returnEmailSentSuccessfully");
		return new EmailResponse(ResponseMessages.EMAIL_SENT_SUCCESSFULLY);
	}

	private EmailResponse returnUnableToSendEmailResponse() {
		logger.debug("Method: returnUnableToSendEmailResponse");
		return new EmailResponse(ResponseMessages.UNABLE_TO_SEND_EMAIL);
	}

	private boolean sendEmail(final EmailRequest request) {
		logger.debug("Method: sendEmail: " + request.getToEmail());
		return smtpHandler.sendEmail(request, properties);
	}	
}
