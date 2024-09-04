package com.app.accountsystem.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="expense")
public class Expenses {
	@Id
	private String expenseNumber;
	private String merchant;
	private Double amount;
	private String category;
	private String reportNumber;
	public String getExpenseNumber() {
		return expenseNumber;
	}
	public void setExpenseNumber(String expenseNumber) {
		this.expenseNumber = expenseNumber;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getReportNumber() {
		return reportNumber;
	}
	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}
}
