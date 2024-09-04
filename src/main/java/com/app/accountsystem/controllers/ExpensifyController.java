package com.app.accountsystem.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.accountsystem.entity.Expenses;
import com.app.accountsystem.entity.ResponseMessage;
import com.app.accountsystem.model.interfaces.impl.ExpenseInputSetting;
import com.app.accountsystem.model.interfaces.impl.PolicyInputSettings;
import com.app.accountsystem.service.ExpensifyService;
import com.opencsv.exceptions.CsvValidationException;

@RestController
public class ExpensifyController {

	@Autowired
	private ExpensifyService expensifyService;
	
	@PostMapping("/expensify/new/policy")
	public ResponseEntity<ResponseMessage> createExpensifyPolicy(@RequestBody PolicyInputSettings policyInputSettings) {
		System.out.println("new Policy Request:" + policyInputSettings.toString());
		return expensifyService.createPolicy(policyInputSettings);
	}

	@PostMapping("/expensify/new/expense")
	public ResponseEntity<ResponseMessage> createExpensifyExpenses(@RequestBody ExpenseInputSetting expenseInputSetting) {
		System.out.println("new Policy Request:" + expenseInputSetting.toString());
		return expensifyService.createExpense(expenseInputSetting);
	}
	
	@GetMapping("/expensify/expenses")
	public List<Expenses> getAllExpenses() throws CsvValidationException, FileNotFoundException, IOException {
		return expensifyService.getAllExpenses();
	}
	
	@GetMapping("/expensify/expenses/{id}")
	public Expenses getExpenseById(@PathVariable String id) {
		return expensifyService.getExpenseById(id);
	}
	
	@GetMapping("/expensify/expenses/{vendor}")
	public List<Expenses> getExpensesByVendorName(@PathVariable String vendorName) {
		return expensifyService.getExpensesByVendorName(vendorName);
	}
}
