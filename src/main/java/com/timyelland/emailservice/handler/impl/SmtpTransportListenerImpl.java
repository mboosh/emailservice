package com.timyelland.emailservice.handler.impl;

import javax.mail.Message;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

import com.timyelland.emailservice.constants.ResponseMessages;
import com.timyelland.emailservice.data.EmailResponse;

public class SmtpTransportListenerImpl implements TransportListener {
	
	private EmailResponse emailResponse;

	public SmtpTransportListenerImpl(final EmailResponse emailResponse) {
		this.emailResponse = emailResponse;
	}

	@Override
	public void messageDelivered(TransportEvent event) {
		emailResponse.setMessage(ResponseMessages.EMAIL_SENT_SUCCESSFULLY);
	}

	@Override
	public void messageNotDelivered(TransportEvent event) {
		emailResponse.setMessage(ResponseMessages.UNABLE_TO_SEND_EMAIL);
		final Message msg = event.getMessage();
	}

	@Override
	public void messagePartiallyDelivered(TransportEvent e) {
		emailResponse.setMessage(ResponseMessages.UNABLE_TO_SEND_EMAIL);
	}

}
