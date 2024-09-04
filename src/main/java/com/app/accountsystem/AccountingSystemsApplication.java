package com.app.accountsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountingSystemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountingSystemsApplication.class, args);
	}
}
