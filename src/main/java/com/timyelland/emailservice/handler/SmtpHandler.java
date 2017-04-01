package com.timyelland.emailservice.handler;

import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.SmtpProperties;

public interface SmtpHandler {	
	boolean sendEmail(EmailRequest request, SmtpProperties smtpProps);
}
