package com.Transaction.transaction;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.*;

@SpringBootApplication
@EnableTransactionManagement
public class TransactionApplication extends Authenticator {
	public static void main(String[] args){
		SpringApplication.run(TransactionApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
