package com.app.accountsystem.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.app.accountsystem.entity.Credentials;
import com.app.accountsystem.entity.ExportRequestJobDescription;
import com.app.accountsystem.entity.OnReceive;
import com.app.accountsystem.entity.OutputSettings;
import com.app.accountsystem.entity.RequestJobDescription;
import com.app.accountsystem.entity.ResponseMessage;
import com.app.accountsystem.model.interfaces.InputSettings;
import com.app.accountsystem.model.interfaces.impl.ExportInputSettings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExpensifyUtil {

	ResponseMessage responseMessage;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private Credentials credentials;
	public ResponseMessage handleError(String message) {
		responseMessage = new ResponseMessage();
		responseMessage.setMesssage(message);
		responseMessage.setStatusCode(HttpStatus.BAD_REQUEST.name());
		return responseMessage;
	}

	public ResponseMessage handleSuccess() {
		responseMessage = new ResponseMessage();
		responseMessage.setMesssage("Request was succesful");
		responseMessage.setStatusCode(HttpStatus.ACCEPTED.name());
		return responseMessage;
	}

	public String handleResponseForExport(ResponseEntity<String> response) {
		if (response.getStatusCode().is2xxSuccessful()) {
			try {
				JsonNode resObj = objectMapper.readTree(response.getBody());
				int responseCode = resObj.get("responseCode").asInt();
				if (responseCode < 400) {
					return resObj.get("filename").asText();
				} else {
					handleError(resObj.get("responseMessage").asText());
				}
			} catch (JsonProcessingException e) {
				handleError(e.getMessage());
			}
		} else {
			handleError("Internal Server Error");
		}
		return null;
	}
	
	public ResponseEntity<ResponseMessage> handleResponse(ResponseEntity<String> response) {
		if (response.getStatusCode().is2xxSuccessful()) {
			try {
				JsonNode resObj = objectMapper.readTree(response.getBody());
				int responseCode = resObj.get("responseCode").asInt();
				if (responseCode < 400) {
					handleSuccess();
				} else {
					handleError(resObj.get("responseMessage").asText());
				}
			} catch (JsonProcessingException e) {
				handleError(e.getMessage());
			}
		} else {
			handleError("Internal Server Error");
		}
		return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
	}
	
	public ExportRequestJobDescription exportRequestDescription() {
		ExportRequestJobDescription requestJobDescription = new ExportRequestJobDescription();
		requestJobDescription.setType("file");
		requestJobDescription.setCredentials(credentials);
		OnReceive onReceive = new OnReceive();
		List<String> onReceiveList = new ArrayList<>();
		onReceiveList.add("returnRandomFileName");
		onReceive.setImmediateResponse(onReceiveList);
		requestJobDescription.setOnReceive(onReceive);
		ExportInputSettings inputSettings = new ExportInputSettings();
		requestJobDescription.setInputSettings(inputSettings);
		OutputSettings outputSettings = new OutputSettings();
		outputSettings.setFileExtension("csv");
		requestJobDescription.setOutputSettings(outputSettings);
		return requestJobDescription;
	}
	
	public RequestJobDescription getRequestJobDescription(InputSettings expenseInputSetting, String type) {
		RequestJobDescription requestJobDescription = new RequestJobDescription();
		requestJobDescription.setType(type);
		requestJobDescription.setCredentials(credentials);
		requestJobDescription.setInputSettings(expenseInputSetting);
		return requestJobDescription;
	}
}
