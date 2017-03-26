package com.timyelland.emailservice.data;

import com.timyelland.emailservice.constants.ResponseMessages;

public class EmailResponse {
	private ResponseMessages message;

	public EmailResponse(ResponseMessages message) {
		this.message = message;
	}

	public ResponseMessages getMessage() {
		return message;
	}

	public void setMessage(ResponseMessages message) {
		this.message = message;
	}

}
