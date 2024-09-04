package com.app.accountsystem.entity;

public class OutputSettings {
	private String fileExtension;

	// Getters and setters

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Override
	public String toString() {
		return "OutputSettings{" + "fileExtension='" + fileExtension + '\'' + '}';
	}
}