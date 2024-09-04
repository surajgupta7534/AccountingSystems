package com.app.accountsystem.entity;

import java.io.Serializable;

import com.app.accountsystem.model.interfaces.InputSettings;

public class ExportRequestJobDescription implements Serializable {

	private String type;
	private Credentials credentials;
	private OnReceive onReceive;
	private InputSettings inputSettings;
	private OutputSettings outputSettings;

	// Getters and setters

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

	public OnReceive getOnReceive() {
		return onReceive;
	}

	public void setOnReceive(OnReceive onReceive) {
		this.onReceive = onReceive;
	}

	public InputSettings getInputSettings() {
		return inputSettings;
	}

	public void setInputSettings(InputSettings inputSettings) {
		this.inputSettings = inputSettings;
	}

	public OutputSettings getOutputSettings() {
		return outputSettings;
	}

	public void setOutputSettings(OutputSettings outputSettings) {
		this.outputSettings = outputSettings;
	}

	@Override
	public String toString() {
		return "RequestJobDescription{" + "type='" + type + '\'' + ", credentials=" + credentials + ", onReceive="
				+ onReceive + ", inputSettings=" + inputSettings + ", outputSettings=" + outputSettings + '}';
	}

}
