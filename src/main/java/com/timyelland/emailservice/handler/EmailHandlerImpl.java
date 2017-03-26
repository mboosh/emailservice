package com.timyelland.emailservice.handler;

import java.util.Objects;

import com.timyelland.emailservice.data.EmailProviderSmtpProperties;
import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;

public class EmailHandlerImpl implements EmailHandler {
	private EmailHandler nextHandler;
	private EmailProviderSmtpProperties properties;

	public EmailHandlerImpl(EmailProviderSmtpProperties properties) {
		this.properties = properties;
	}	

	@Override
	public void nextHandler(EmailHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	@Override
	public EmailResponse handleRequest(EmailRequest request) {
		final boolean response = sendEmail(request);
		return response ? returnEmailSentSuccessfullyResponse() : Objects.isNull(nextHandler) ? returnUnableToSendEmailResponse()  : nextHandler.handleRequest(request);
	}
	
	private EmailResponse returnEmailSentSuccessfullyResponse() {
		return new EmailResponse("Email Sent Successfully");
	}

	private EmailResponse returnUnableToSendEmailResponse() {
		return new EmailResponse("We have being unable to send your email!");
	}

	private boolean sendEmail(final EmailRequest request) {
		return SmtpHandler.sendEmail(request, properties);
	}
	
}
