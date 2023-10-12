package com.astro.erpAcs.util;

import java.time.Instant;

public class MessageResponse {

	private String message;
	private StatusMessage statusMessage;
	private Instant timestamp = Instant.now();
	
	@Deprecated
	public MessageResponse() {}

	public MessageResponse(String message, StatusMessage statusMessage) {
		this.message = message;
		this.statusMessage = statusMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StatusMessage getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(StatusMessage statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	
}
