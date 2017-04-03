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

/*
 * EmailManager handles the conversion of the HttpServletRequest onto 
 * EmailRequest which is in turn handles by the EmailHandlers
 * in a chain of responsibility sequence until one of them
 * is able to send the email.
 */
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
	
	/*
	 * Create singleton instance of EmailManager
	 */
	public static EmailManager get(final SmtpHandler smtpHandler) {
		logger.debug("Method: get");
		if (Objects.isNull(emailManager)) {
			logger.debug("Creating new EmailManager instance!");
			emailManager = new EmailManager(smtpHandler);
			emailManager.init();
		}
		return emailManager;
	}

	/*
	 * load the smtp property files and set the email handler chain
	 * this chain dictates the sequence in which smtp servers will called
	 */
	private void init() {
		logger.debug("Method: init");
		final EmailHandler amazonUsWest2Handler = new EmailHandlerImpl(readProperties(AMAZON_SES_US_WEST_2_PROPERTIES), smtpHandler);
		final EmailHandler amazonEuWest1Handler = new EmailHandlerImpl(readProperties(AMAZON_SES_EU_WEST_1_PROPERTIES), smtpHandler);
		final EmailHandler amazonUsEast1 = new EmailHandlerImpl(readProperties(AMAZON_SES_US_EAST_1_PROPERTIES), smtpHandler);
		
		amazonUsWest2Handler.nextHandler(amazonEuWest1Handler);
		amazonEuWest1Handler.nextHandler(amazonUsEast1);
		
		this.emailHandler = amazonUsWest2Handler;
	}
	
	/*
	 * read in the smtp property files for the email servers
	 */
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

	/*
	 * Process an EmailRequest this hands it to the chain of smtp
	 * servers to try send the email.
	 */
	public EmailResponse process(BufferedReader reader) {		
		logger.debug("Method: process");
		final EmailResponse response = EmailResponse.create();
		final EmailRequest request = mapEmailRequest(reader, response);		
		if (Objects.isNull(request)) {
			logger.error("Null EmailRequest returned");
			return response;
		}		
		emailHandler.handleRequest(request, response);
		return response;
	}
	
	/*
	 * Map the request object to EmailRequest.
	 */
	private EmailRequest mapEmailRequest(final BufferedReader reader, final EmailResponse response) {
		logger.debug("Method: mapEmailRequest");
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(reader, EmailRequest.class);
		} catch (IOException ex) {
			logger.error("Exception mapping email request", ex);
			response.setMessage(ResponseMessages.UNABLE_TO_MAP_REQUEST_OBJECT, ex.getMessage());
			return null;
		}
	}	
}
