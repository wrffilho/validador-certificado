package br.am.gov.prodam.validadorcertificado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ValidadorCertificadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidadorCertificadoApplication.class, args);
	}

}
