package com.app.accountsystem.entity;

import java.io.Serializable;

import com.app.accountsystem.model.interfaces.InputSettings;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RequestJobDescription implements Serializable {
	private String type;
	private Credentials credentials;
	private InputSettings inputSettings;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public InputSettings getInputSettings() {
		return inputSettings;
	}
	public void setInputSettings(InputSettings inputSettings) {
		this.inputSettings = inputSettings;
	}
	@Override
	public String toString() {
		return "RequestJobDescription [type=" + type + ", credentials=" + credentials + ", inputSettings="
				+ inputSettings + "]";
	}
	
	
}
