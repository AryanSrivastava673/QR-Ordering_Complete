package com.aryansrivastava.qrOrdering.QrOrdering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class QrOrderingApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrOrderingApplication.class, args);
	}

}
