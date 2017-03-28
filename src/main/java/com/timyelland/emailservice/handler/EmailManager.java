package com.timyelland.emailservice.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timyelland.emailservice.constants.ResponseMessages;
import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;
import com.timyelland.emailservice.data.SmtpProperties;
import com.timyelland.emailservice.handler.impl.EmailHandlerImpl;
import com.timyelland.emailservice.servlet.EmailServiceServlet;

public class EmailManager {
	final static Logger logger = Logger.getLogger(EmailManager.class);
	private static final String AMAZON_SES_US_WEST_2_PROPERTIES = "email-smtp.us-west-2.amazonaws.com.properties";	
	private static final String AMAZON_SES_EU_WEST_1_PROPERTIES = "email-smtp.eu-west-1.amazonaws.com.properties";
	private static final String AMAZON_SES_US_EAST_1_PROPERTIES = "email-smtp.us-east-1.amazonaws.com.properties";
	
	private static EmailManager emailManager;
	private EmailHandler emailHandler;	
	private SmtpHandler smtpHandler;
	
	
	private EmailManager(final SmtpHandler smtpHandler) {
		this.smtpHandler = smtpHandler;
	}
	
	public static EmailManager get(final SmtpHandler smtpHandler) {
		logger.debug("Method: get");
		if (Objects.isNull(emailManager)) {
			logger.debug("Creating new EmailManager instance!");
			emailManager = new EmailManager(smtpHandler);
			emailManager.init();
		}
		return emailManager;
	}

	private void init() {
		logger.debug("Method: init");
		final EmailHandler amazonUsWest2Handler = new EmailHandlerImpl(readProperties(AMAZON_SES_US_WEST_2_PROPERTIES));
		final EmailHandler amazonEuWest1Handler = new EmailHandlerImpl(readProperties(AMAZON_SES_EU_WEST_1_PROPERTIES));
		final EmailHandler amazonUsEast1 = new EmailHandlerImpl(readProperties(AMAZON_SES_US_EAST_1_PROPERTIES));
		
		amazonUsWest2Handler.nextHandler(amazonEuWest1Handler, smtpHandler);
		amazonEuWest1Handler.nextHandler(amazonUsEast1, smtpHandler);
		
		this.emailHandler = amazonUsWest2Handler;
	}
	
	private SmtpProperties readProperties(final String fileName) {
		logger.debug("Method: readProperties: " + fileName);
		final Properties props = new Properties();
		final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		if (inputStream != null) {
			try {
				props.load(inputStream);
			} catch (IOException ex) {
				logger.error("Exception reading property file", ex);
				return null;
			}
		}		
		return new SmtpProperties().set(props);
	}

	public EmailResponse process(BufferedReader reader) {
		logger.debug("Method: process");
		final EmailRequest emailRequest = mapEmailRequest(reader);
		if (Objects.isNull(emailRequest)) {
			logger.debug("Null EmailRequest returned");
			return setupErrorEmailResponse();
		}		
		return emailHandler.handleRequest(emailRequest);
	}
	
	private EmailResponse setupErrorEmailResponse() {
		logger.debug("Method: setupErrorEmailResponse");
		return new EmailResponse(ResponseMessages.UNABLE_TO_MAP_REQUEST_OBJECT);
	}

	private EmailRequest mapEmailRequest(final BufferedReader reader) {
		logger.debug("Method: mapEmailRequest");
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(reader, EmailRequest.class);
		} catch (IOException ex) {
			logger.error("Exception mapping email request", ex);
			return null;
		}
	}	
}
