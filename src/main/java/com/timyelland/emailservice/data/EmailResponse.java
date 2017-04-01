package com.timyelland.emailservice.data;

import com.timyelland.emailservice.constants.ResponseMessages;

public class EmailResponse {
	private ResponseMessages message;
	private String messageDetail;

	public EmailResponse(ResponseMessages message) {
		this.message = message;
	}
	
	public EmailResponse() {
		this.message = ResponseMessages.EMAIL_SENT_SUCCESSFULLY;
	}

	public ResponseMessages getMessage() {
		return message;
	}
	
	public void setMessage(final ResponseMessages responseMessage) {
		this.message = responseMessage;
	}

	public String getMessageDetail() {
		return messageDetail;
	}

	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
	}	
	
}
