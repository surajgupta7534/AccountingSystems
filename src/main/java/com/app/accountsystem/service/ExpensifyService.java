package com.app.accountsystem.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.app.accountsystem.entity.Credentials;
import com.app.accountsystem.entity.DownloadRequestJobDescription;
import com.app.accountsystem.entity.Expenses;
import com.app.accountsystem.entity.ExportRequestJobDescription;
import com.app.accountsystem.entity.RequestJobDescription;
import com.app.accountsystem.entity.ResponseMessage;
import com.app.accountsystem.model.interfaces.impl.ExpenseInputSetting;
import com.app.accountsystem.model.interfaces.impl.PolicyInputSettings;
import com.app.accountsystem.repository.ExpenseRepository;
import com.app.accountsystem.scheduledTask.FetchReportsFromExpensify;
import com.app.accountsystem.utility.ExpensifyUtil;
import com.app.accountsystem.utility.MultipartFileResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class ExpensifyService {

	private static final String CREATE = "create";
	private static final String EXPENSIFY_APP_URL = "https://integrations.expensify.com/Integration-Server/ExpensifyIntegrations";

	private RestTemplate restTemplate;
	private Credentials credentials;
	private ExpenseRepository expenseRepository;
	private List<Expenses> listOfExpenses;
	private ExpensifyUtil util;
	private FetchReportsFromExpensify expensesExpoter;

	public ExpensifyService(final RestTemplate restTemplate, final Credentials credentials,
			final ExpenseRepository expenseRepository, final ExpensifyUtil expensifyUtil,
			FetchReportsFromExpensify expensesExpoter) {
		this.restTemplate = restTemplate;
		this.credentials = credentials;
		this.expenseRepository = expenseRepository;
		this.util = expensifyUtil;
		this.expensesExpoter = expensesExpoter;
	}

	//This is to add expenses in directly to Expensify Account. Not in local database
	public ResponseEntity<ResponseMessage> createExpense(ExpenseInputSetting expenseInputSetting) {
		RequestJobDescription request = util.getRequestJobDescription(expenseInputSetting, CREATE);

		System.out.println("requets:" + request.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");

		HttpEntity<String> requestEntity = new HttpEntity<>("requestJobDescription=" + request.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange(EXPENSIFY_APP_URL, HttpMethod.POST, requestEntity,
				String.class);

		return util.handleResponse(response);
	}

	//This API is to add a new Policy in directly to Expensify Account. Not in local database
	public ResponseEntity<ResponseMessage> createPolicy(PolicyInputSettings policyInputSettings) {
		RequestJobDescription request = util.getRequestJobDescription(policyInputSettings, CREATE);
		request.setType(CREATE);
		request.setCredentials(credentials);
		request.setInputSettings(policyInputSettings);
		System.out.println("requets:" + request.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");

		HttpEntity<String> requestEntity = new HttpEntity<>("requestJobDescription=" + request, headers);

		ResponseEntity<String> response = restTemplate.exchange(EXPENSIFY_APP_URL, HttpMethod.POST, requestEntity,
				String.class);

		return util.handleResponse(response);
	}

	//Fetch All Expenses for a particular vendor.
	public List<Expenses> getExpensesByVendorName(String vendorName) {
		List<Expenses> vendorsExpenses = new ArrayList<>();
		listOfExpenses = expensesExpoter.getAllExpenses();
		for(Expenses ex : listOfExpenses) {
			if(ex.getMerchant().equals(vendorName)) {
				vendorsExpenses.add(ex);
			}
		}
		return vendorsExpenses;
	}

	//Fetch expense by it's id.
	public Expenses getExpenseById(String id) {
		Optional<Expenses> expense = expenseRepository.findById(id);
		if(expense.isPresent()) {
			return expense.get();
		}
		return null;
	}

	//Get all expenses.
	public List<Expenses> getAllExpenses() {
		return expensesExpoter.getAllExpenses();
	}
}
