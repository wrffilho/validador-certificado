package br.am.gov.prodam.validadorcertificado.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ConfigurarCertificadoSefazService  {

	@Value("${ssl.key-store}")
	private String keyStorePath;

	@Value("${ssl.key-store-password}")
	private String keyStorePassword;

	@Value("${ssl.trust-store}")
	private String trustStorePath;

	@Value("${ssl.trust-store-password}")
	private String trustStorePassword;

	@Value("${ssl.pem-file-name}")
	private String pemFileName;

	@Value("${ssl.url}")
	private String url;

	@Value("${ssl.delete-command}")
	private String deleteCommand;

	@Value("${ssl.import-command}")
	private String importCommand;

	public void iniciar() {
		try {
			getCertificado();
		} catch (Exception e) {
			log.error("Erro ao configurar o certificado", e);
		}
	}

	private void getCertificado() throws IOException, InterruptedException, CertificateEncodingException {
		Certificate certificado = recuperarCertificadoDoServidor(url);
		gerarPemFile(pemFileName, certificado);
		deleteJKS(deleteCommand);
		//importarCertificado(importCommand);
		//setPropriedades();
	}

	// import java.net.URI;

	// Dentro do método recuperarCertificadoDoServidor
	private Certificate recuperarCertificadoDoServidor(String url) throws IOException {
		log.info("Baixando certificado do servidor");
		URI uri = URI.create(url);
		HttpsURLConnection connection = (HttpsURLConnection) uri.toURL().openConnection();
		connection.connect();
		Certificate[] certificates = connection.getServerCertificates();
		return certificates[0];
	}

	private void gerarPemFile(String pfName, Certificate cert) throws IOException, CertificateEncodingException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(pfName))) {
			writer.write("-----BEGIN CERTIFICATE-----\n");
			writer.write(DatatypeConverter.printBase64Binary(cert.getEncoded()).replaceAll("(.{64})", "$1\n"));
			writer.write("\n-----END CERTIFICATE-----\n");
		}
		log.info("Certificado salvo em: {}", pfName);
	}

	private void deleteJKS(String dc) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(dc.split("\\s+"));
		Process deleteProcess = processBuilder.inheritIO().start();
		deleteProcess.waitFor();
	}

	/*private void importarCertificado(String ic) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(ic.split("\\s+"));
		Process importProcess = processBuilder.inheritIO().start();
		int exitCode = importProcess.waitFor();
		if (exitCode == 0) {
			log.info("Certificado importado para o keystore JKS com sucesso.");
			deletePemFile(pemFileName);
		} else {
			log.error("Erro ao importar o certificado para o keystore JKS.");
		}
	}

	private void deletePemFile(String pfName) {
		File pemFile = new File(pfName);
		if (pemFile.delete()) {
			log.info("Arquivo PEM excluído com sucesso.");
		} else {
			log.error("Erro ao excluir arquivo {}", pfName);
		}
	}

	private void setPropriedades() {
		System.setProperty("javax.net.ssl.keyStore", keyStorePath);
		System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
		System.setProperty("javax.net.ssl.trustStore", trustStorePath);
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
		log.info("System javax.net.ssl.keyStore {}", System.getProperty("javax.net.ssl.keyStore"));
		log.info("System javax.net.ssl.keyStorePassword {}", System.getProperty("javax.net.ssl.keyStorePassword"));
		log.info("System javax.net.ssl.trustStore {}", System.getProperty("javax.net.ssl.trustStore"));
		log.info("System javax.net.ssl.trustStorePassword {}", System.getProperty("javax.net.ssl.trustStorePassword"));
	}*/
}
