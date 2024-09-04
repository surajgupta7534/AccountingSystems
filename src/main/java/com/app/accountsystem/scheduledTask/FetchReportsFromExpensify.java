package com.app.accountsystem.scheduledTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.app.accountsystem.entity.Credentials;
import com.app.accountsystem.entity.DownloadRequestJobDescription;
import com.app.accountsystem.entity.Expenses;
import com.app.accountsystem.entity.ExportRequestJobDescription;
import com.app.accountsystem.repository.ExpenseRepository;
import com.app.accountsystem.utility.ExpensifyUtil;
import com.app.accountsystem.utility.MultipartFileResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Component
public class FetchReportsFromExpensify {
	
	private static final String EXPENSIFY_APP_URL = "https://integrations.expensify.com/Integration-Server/ExpensifyIntegrations";

	private RestTemplate restTemplate;
	private Credentials credentials;
	private ExpenseRepository expenseRepository;
	private List<Expenses> listOfExpenses;
	private ExpensifyUtil util;
	
	public FetchReportsFromExpensify(final RestTemplate restTemplate, final Credentials credentials,
			final ExpenseRepository expenseRepository, final ExpensifyUtil expensifyUtil) {
		this.restTemplate = restTemplate;
		this.credentials = credentials;
		this.expenseRepository = expenseRepository;
		this.util = expensifyUtil;
	}

	/*
	 * This task will fetch all the expenses from expensify directly
	 * Step1: we will generate the expenses report on expensify
	 *  Step 1.1: After calling API to generate reports on Expensify. Expensify will return a response
	 *  		which will have fileName field.
	 *  Step 2: By using downloader API we will fetch the file and will store to local machine.
	 *  Step 3: After downloading csv file we will convert it to java objects and store to Mongodb database.
	 *  Note: This task is scheduled for fetching expense Report after every 5 minutes. 
	 */
	@Scheduled(fixedRate=300000)
	public void fetchTransactions() throws IOException, CsvValidationException {
		ExportRequestJobDescription requestJobDescription = util.exportRequestDescription();

		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = null;
		try {
			requestBody = objectMapper.writeValueAsString(requestJobDescription);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create the HTTP entity with form data
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		// Attach the file
		Resource fileResource = new ClassPathResource("expensify_template.ftl");
		MultipartFile multipartFile;
		multipartFile = new MultipartFileResource(fileResource);

		HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

		// Send the POST request
		ResponseEntity<String> response = restTemplate.exchange(EXPENSIFY_APP_URL, HttpMethod.POST, requestEntity,
				String.class);

		String fileNameToDownload = util.handleResponseForExport(response);
		if (fileNameToDownload != null) {
			downloadExpenseReport(fileNameToDownload);
		}
		loadDataToDatabase();
	}

	public List<Expenses> loadDataToDatabase(){
		listOfExpenses = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(new ClassPathResource("ExpenseReport.csv").getFile()))) {
            try {
				String[] header = reader.readNext();
			} catch (CsvValidationException e) {
				e.printStackTrace();
			}
            String[] nextLine;
            try {
				while ((nextLine = reader.readNext()) != null) {
				    Expenses expense = new Expenses();
				    expense.setMerchant(nextLine[0]);
				    expense.setAmount(Double.parseDouble(nextLine[1]));
				    expense.setCategory(nextLine[2]);
				    expense.setReportNumber(nextLine[3]);
				    expense.setExpenseNumber(nextLine[4]);
				    listOfExpenses.add(expense);
				}
			} catch (CsvValidationException | NumberFormatException e) {
				e.printStackTrace();
			}
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        expenseRepository.saveAll(listOfExpenses);
        return listOfExpenses;
	}

	private void downloadExpenseReport(String fileNameToDownload) throws IOException {
		DownloadRequestJobDescription requestJobDescription = new DownloadRequestJobDescription();
		requestJobDescription.setCredentials(credentials);
		requestJobDescription.setFileName(fileNameToDownload);
		requestJobDescription.setFileSystem("integrationServer");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<DownloadRequestJobDescription> entity = new HttpEntity<>(requestJobDescription, headers);

		ResponseEntity<Resource> response = restTemplate.exchange(EXPENSIFY_APP_URL, HttpMethod.POST, entity,
				Resource.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Resource resource = response.getBody();
			if (resource != null) {
				File file = new File(new ClassPathResource("ExpenseReport.csv").getURI());

				try (InputStream inputStream = resource.getInputStream();
						FileOutputStream fileOutputStream = new FileOutputStream(file)) {
					byte[] buffer = new byte[1024];
					int bytesRead;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						fileOutputStream.write(buffer, 0, bytesRead);
					}
				}
				System.out.println("File downloaded and saved to src/main/resources successfully");
			} else {
				System.out.println("Failed to download file");
			}
		} else {
			System.out.println("Request failed with status: " + response.getStatusCode());
		}
	}
	
	public List<Expenses> getAllExpenses() {
		return this.listOfExpenses;
	}
}
