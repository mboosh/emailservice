package com.timyelland.emailservice.handler;

import org.junit.Test;

import com.timyelland.emailservice.handler.impl.EmailHandlerImpl;

public class EmailHandlerTest {	
	@Test
	public void testPositiveEmailHandlerChain() {
		
		final EmailHandler handler1 = new EmailHandlerImpl(null);
		final EmailHandler handler2 = new EmailHandlerImpl(null);
		final EmailHandler handler3 = new EmailHandlerImpl(null);
		
		handler1.nextHandler(handler2, new TestSmtpHandlerImpl());
		handler1.nextHandler(handler3, new TestSmtpHandlerImpl());
		
		handler1.handleRequest(null);
	}

}
