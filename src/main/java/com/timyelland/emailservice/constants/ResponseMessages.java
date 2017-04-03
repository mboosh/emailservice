package com.timyelland.emailservice.constants;

public enum ResponseMessages {
	EMAIL_PARTIALLY_DELIVERED("Email partially delivered!"),
	EMAIL_SENT_SUCCESSFULLY("Email sent successfully!"),
	MIME_MESSAGE_EXCEPTION("Exception creating message!"),
	UNABLE_TO_MAP_REQUEST_OBJECT("Unable to map request object."),
	UNABLE_TO_SEND_EMAIL("Unable To Send Email"),; 
	
	private String message;

	private ResponseMessages(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
