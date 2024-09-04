package com.app.accountsystem.entity;

import java.util.List;

public class OnReceive {
	private List<String> immediateResponse;

	// Getters and setters

	public List<String> getImmediateResponse() {
		return immediateResponse;
	}

	public void setImmediateResponse(List<String> immediateResponse) {
		this.immediateResponse = immediateResponse;
	}

	@Override
	public String toString() {
		return "OnReceive{" + "immediateResponse=" + immediateResponse + '}';
	}
}
