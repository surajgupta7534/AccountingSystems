package com.app.accountsystem.model.interfaces.impl;

import java.io.Serializable;

import org.springframework.lang.NonNull;

import com.app.accountsystem.model.interfaces.InputSettings;

public class PolicyInputSettings implements InputSettings, Serializable {
	private static final long serialVersionUID = 1L;

	private final String type = "policy";
	@NonNull
	private String policyName;

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	@Override
	public String toString() {
		return "PolicyInputSettings [type=" + type + ", policyName=" + policyName + "]";
	}

}
