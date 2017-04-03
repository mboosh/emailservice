package com.timyelland.emailservice.handler;

import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;

public interface EmailHandler {	  
	void nextHandler(EmailHandler handler);
	boolean handleRequest(EmailRequest request, EmailResponse response);
}

