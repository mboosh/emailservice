package com.timyelland.emailservice.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.timyelland.emailservice.data.EmailResponse;
import com.timyelland.emailservice.handler.EmailManager;
import com.timyelland.emailservice.handler.impl.SmtpHandlerImpl;

@WebServlet("/service")
public class EmailServiceServlet extends HttpServlet {
	final static Logger logger = Logger.getLogger(EmailServiceServlet.class);
	
	private static final long serialVersionUID = 3695957158727479323L;

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	/*
	 * instantiates a singleton EmailManager to handle the request
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {		
		logger.debug("Method: doPost");		
		final EmailManager emailManager = EmailManager.get(new SmtpHandlerImpl());
		setupResponse(emailManager.process(request.getReader()), response);		
	}

	/*
	 * Convert's the EmailResponse to a JSON message and writes it to the servlet response
	 */
	private void setupResponse(final EmailResponse emailResponse, final HttpServletResponse response) {
		logger.debug("Method: setupResponse()");
		String json = new Gson().toJson(emailResponse.getMessages());
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
