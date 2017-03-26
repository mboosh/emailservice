package com.timyelland.emailservice.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timyelland.emailservice.data.EmailProviderSmtpProperties;
import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;

public class EmailManager {
	private static EmailManager emailManager;
	
	private static final String AMAZON_SES_PROPERTIES = "amazonses.smtp.properties";
	private EmailHandler emailHandler;
	
	
	private EmailManager() {
	}
	
	public static EmailManager get() {
		if (Objects.isNull(emailManager)) {
			emailManager = new EmailManager();
			emailManager.init();
		}
		return emailManager;
	}

	private void init() {
		final EmailHandler amazonHandler = new EmailHandlerImpl(readProperties(AMAZON_SES_PROPERTIES));
		final EmailHandler somOtherHandler = new EmailHandlerImpl(readProperties(AMAZON_SES_PROPERTIES));
		amazonHandler.nextHandler(somOtherHandler);
		this.emailHandler = amazonHandler;
	}
	
	private EmailProviderSmtpProperties readProperties(final String fileName) {
		final Properties props = new Properties();
		final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		if (inputStream != null) {
			try {
				props.load(inputStream);
			} catch (IOException e) {
				return null;
			}
		}		
		return new EmailProviderSmtpProperties().set(props);
	}

	public EmailResponse process(BufferedReader reader) {
		final EmailRequest emailRequest = mapEmailRequest(reader);
		if (Objects.isNull(emailRequest)) {
			return setupErrorEmailResponse();
		}		
		return emailHandler.handleRequest(emailRequest);
	}
	
	private EmailResponse setupErrorEmailResponse() {
		return null;
	}

	private EmailRequest mapEmailRequest(final BufferedReader reader) {		
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(reader, EmailRequest.class);
		} catch (IOException e) {
			return null;
		}
	}
	
	
	
	
	
}
