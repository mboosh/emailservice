package com.timyelland.emailservice.handler;

import java.util.Objects;

import org.apache.log4j.Logger;

import com.timyelland.emailservice.constants.ResponseMessages;
import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;
import com.timyelland.emailservice.data.SmtpProperties;

public class EmailHandlerImpl implements EmailHandler {
	final static Logger logger = Logger.getLogger(EmailHandlerImpl.class);
	
	private EmailHandler nextHandler;
	private SmtpProperties properties;

	public EmailHandlerImpl(SmtpProperties properties) {
		this.properties = properties;
	}	

	@Override
	public void nextHandler(EmailHandler nextHandler) {
		logger.debug("Method: nextHandler");
		this.nextHandler = nextHandler;
	}

	@Override
	public EmailResponse handleRequest(EmailRequest request) {
		logger.debug("Method: handleRequest");
		final boolean successful = sendEmail(request);
		logger.debug("Sending email was: " + successful);
		return successful ? returnEmailSentSuccessfullyResponse() : Objects.isNull(nextHandler) ? returnUnableToSendEmailResponse()  : nextHandler.handleRequest(request);
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
		return SmtpHandler.sendEmail(request, properties);
	}
	
}
