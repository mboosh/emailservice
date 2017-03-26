package com.timyelland.emailservice.constants;

public enum ResponseMessages {
	UNABLE_TO_MAP_REQUEST_OBJECT("Server Error: Unable To Map Request Object!"), 
	EMAIL_SENT_SUCCESSFULLY("Email Sent Successfully!"), 
	UNABLE_TO_SEND_EMAIL("We have being unable to send your email!"),;
	
	private String message;

	ResponseMessages(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
