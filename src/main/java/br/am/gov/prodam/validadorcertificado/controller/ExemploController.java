package br.am.gov.prodam.validadorcertificado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.am.gov.prodam.validadorcertificado.service.ConfigurarCertificadoSefazService;
import br.am.gov.prodam.validadorcertificado.service.SefazService;

@RestController
@RequestMapping("/api/v1")
public class ExemploController {

	@Autowired
	private SefazService sefazService;
	@Autowired
	private ConfigurarCertificadoSefazService certificadoSefazService;
	@GetMapping("/exemplo")
	public ResponseEntity<String> exemploPost() {
		
		//sefazService.atualizarCertificado();
		certificadoSefazService.iniciar();
		System.out.println("Recebido GET com corpo: ");

		return new ResponseEntity<>("GET recebido com sucesso", HttpStatus.OK);
	}

}
