package com.app.accountsystem.model.interfaces.impl;

import java.io.Serializable;
import java.util.List;

import com.app.accountsystem.entity.Transaction;
import com.app.accountsystem.model.interfaces.InputSettings;
import com.mongodb.lang.NonNull;

public class ExpenseInputSetting implements InputSettings, Serializable{

	private static final long serialVersionUID = 1L;

	private final String type = "expenses";

	@NonNull
	private String employeeEmail;

	private List<Transaction> transactions;

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "ExpenseInputSetting [type=" + type + ", employeeEmail=" + employeeEmail + ", transactions="
				+ transactions + "]";
	}
}
