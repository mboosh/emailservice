package com.timyelland.emailservice.handler.impl;

import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

import com.timyelland.emailservice.constants.ResponseMessages;
import com.timyelland.emailservice.data.EmailResponse;

/*
 * TransportListener to listen to the response when sending emails.
 */
public class SmtpTransportListenerImpl implements TransportListener {
	
	private EmailResponse emailResponse;

	public SmtpTransportListenerImpl(final EmailResponse emailResponse) {
		this.emailResponse = emailResponse;
	}

	@Override
	public void messageDelivered(TransportEvent event) {
		emailResponse.setMessage(ResponseMessages.EMAIL_SENT_SUCCESSFULLY, null);
	}

	@Override
	public void messageNotDelivered(TransportEvent event) {
		emailResponse.setMessage(ResponseMessages.UNABLE_TO_SEND_EMAIL, null);
	}

	@Override
	public void messagePartiallyDelivered(TransportEvent e) {
		emailResponse.setMessage(ResponseMessages.EMAIL_PARTIALLY_DELIVERED, null);
	}
}
