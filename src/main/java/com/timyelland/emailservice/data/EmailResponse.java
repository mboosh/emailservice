package com.timyelland.emailservice.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.timyelland.emailservice.constants.ResponseMessages;

public class EmailResponse {
	final Map<String,Set<String>> messages = new HashMap<>();

	public Map<String,Set<String>> getMessages() {
		return messages;
	}
	
	public void setMessage(final ResponseMessages responseMessage, final String textMessage) {
		Set<String> textMessages = messages.get(responseMessage.getMessage());
		if (Objects.isNull(textMessages)) {
			textMessages = new HashSet<String>();
			messages.put(responseMessage.getMessage(), textMessages);			
		}
		textMessages.add(textMessage);
	}

	public static EmailResponse create() {
		return new EmailResponse();
	}
}
