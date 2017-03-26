package com.timyelland.emailservice.data;

public class EmailRequest {
	private String toemail;
	private String ccemail;
	private String subject;
	private String content;
	
	public String getToEmail() {
		return toemail;
	}
	public void setToEmail(String toemail) {
		this.toemail = toemail;
	}
	public String getCcEmail() {
		return ccemail;
	}
	public void setCcEmail(String ccemail) {
		this.ccemail = ccemail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
}
