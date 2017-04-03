package com.timyelland.emailservice.handler;

import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;
import com.timyelland.emailservice.data.SmtpProperties;

public interface SmtpHandler {	
	boolean sendEmail(EmailRequest request, EmailResponse response, SmtpProperties smtpProps);
}
