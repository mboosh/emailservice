package com.timyelland.emailservice.data;

import java.util.Objects;

public class EmailRequest {
	private String toEmail;
	private String ccEmail;
	private String subject;
	private String content;
	
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toemail) {
		this.toEmail = toemail;
	}
	public String getCcEmail() {
		return ccEmail;
	}
	public void setCcEmail(String ccemail) {
		this.ccEmail = ccemail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return Objects.nonNull(content) ? content : "";
	}
	public void setContent(String content) {
		this.content = content;
	}	
}
