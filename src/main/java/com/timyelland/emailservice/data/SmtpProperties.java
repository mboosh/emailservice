package com.timyelland.emailservice.data;

import java.util.Properties;

public class SmtpProperties {
	private String port;
	private String host;
	private String userName;
	private String password;
	private String fromEmail;

	public String getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
	public String getFromEmail() {
		return fromEmail;
	}	

	public SmtpProperties set(Properties props) {
		port = props.getProperty("port");
		host = props.getProperty("host");
		userName = props.getProperty("userName");
		password = props.getProperty("password");
		fromEmail = props.getProperty("fromEmail");
		return this;
	}
}
