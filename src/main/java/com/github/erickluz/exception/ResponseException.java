package com.github.erickluz.exception;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.github.erickluz.commons.Util;

public class ResponseException implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mensagem;
	private String timeStamp;

	public ResponseException() {
		this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Util.TIMESTAMP_FORMAT));
	}
	
	public ResponseException(String mensagem) {
		this.mensagem = mensagem;
		this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Util.TIMESTAMP_FORMAT));
	}
	
	public ResponseException(Exception exception) {
		this.mensagem = exception.getMessage();
		this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Util.TIMESTAMP_FORMAT));	
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
