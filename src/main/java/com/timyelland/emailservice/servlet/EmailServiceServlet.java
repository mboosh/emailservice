package com.timyelland.emailservice.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.timyelland.emailservice.data.EmailRequest;
import com.timyelland.emailservice.data.EmailResponse;
import com.timyelland.emailservice.handler.EmailManager;

@WebServlet("/service")
public class EmailServiceServlet extends HttpServlet {
	final static Logger logger = Logger.getLogger(EmailServiceServlet.class);
	
	private static final long serialVersionUID = 3695957158727479323L;

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {		
		logger.debug("Method: doPost");
		final EmailManager emailManager = EmailManager.get();
		setupResponse(emailManager.process(request.getReader()), response);		
	}

	private void setupResponse(final EmailResponse emailResponse, final HttpServletResponse response) {
		logger.debug("Method: setupResponse()");
		String json = new Gson().toJson(emailResponse.getMessage().getMessage());
		logger.debug("Json Value: " + json);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    try {
			response.getWriter().write(json);
		} catch (final IOException ex) {
			logger.error("Error creating response JSON", ex);
		}			
	}



}
