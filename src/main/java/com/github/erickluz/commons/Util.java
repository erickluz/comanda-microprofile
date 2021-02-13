package com.github.erickluz.commons;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class Util {
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String TIMESTAMP_FORMAT = "dd/MM/yyyy HH:mm:ss.SSS";
	@Inject
	@ConfigProperty(name = "http.host")
	String httpHost;
	@Inject
	@ConfigProperty(name = "http.port")
	String httpPort;
	
	public String getUrlServidor() {
		StringBuilder sb = new StringBuilder();
		return sb.append(httpHost).append(":").append(httpPort).append("/").toString();
	}
	
	public LocalDateTime toLocalDateTime(String timestamp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return LocalDateTime.parse(timestamp, formatter); 
		 
	}
	
}
