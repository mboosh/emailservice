package com.timyelland.emailservice.handler;

import org.junit.Test;
import static org.mockito.Mockito.*;

import com.timyelland.emailservice.constants.ResponseMessages;
import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;
import com.timyelland.emailservice.data.SmtpProperties;
import com.timyelland.emailservice.handler.impl.EmailHandlerImpl;

public class EmailHandlerTest {	
	@Test
	public void testPositiveEmailHandlerChain() {
		// mock creation
		final SmtpProperties smtpProperties = mock(SmtpProperties.class);
		final EmailRequest emailRequest = mock(EmailRequest.class);
		final SmtpHandler smtpHandler = mock(SmtpHandler.class);
		
		final EmailHandler handler1 = new EmailHandlerImpl(smtpProperties, smtpHandler);
		final EmailHandler handler2 = new EmailHandlerImpl(smtpProperties, smtpHandler);
		final EmailHandler handler3 = new EmailHandlerImpl(smtpProperties, smtpHandler);
		
		handler1.nextHandler(handler2);
		handler2.nextHandler(handler3);
		
		final EmailResponse emailResponse = EmailResponse.create();
		
		when(smtpHandler.sendEmail(emailRequest, emailResponse, smtpProperties)).thenReturn(true);
		
		handler1.handleRequest(emailRequest, emailResponse);
		assert(emailResponse.getMessages().containsKey(ResponseMessages.EMAIL_SENT_SUCCESSFULLY));
	}
	
	@Test
	public void testNegativeEmailHandlerChain() {
		// mock creation
		final SmtpProperties smtpProperties = mock(SmtpProperties.class);
		final EmailRequest emailRequest = mock(EmailRequest.class);
		final SmtpHandler smtpHandler = mock(SmtpHandler.class);
		
		final EmailHandler handler1 = new EmailHandlerImpl(smtpProperties, smtpHandler);
		final EmailHandler handler2 = new EmailHandlerImpl(smtpProperties, smtpHandler);
		final EmailHandler handler3 = new EmailHandlerImpl(smtpProperties, smtpHandler);
		
		handler1.nextHandler(handler2);
		handler2.nextHandler(handler3);
		
		final EmailResponse emailResponse = EmailResponse.create();		
		
		when(smtpHandler.sendEmail(emailRequest, emailResponse, smtpProperties)).thenReturn(false);
		
		handler1.handleRequest(emailRequest, emailResponse);
		assert(emailResponse.getMessages().containsKey(ResponseMessages.UNABLE_TO_SEND_EMAIL));
	}	

}
