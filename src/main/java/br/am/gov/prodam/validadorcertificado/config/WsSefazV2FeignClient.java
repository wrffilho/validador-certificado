package br.am.gov.prodam.validadorcertificado.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.am.gov.prodam.validadorcertificado.form.SefazCerficadoForm;

@FeignClient(name = "sefazv2", url = "${ws.sefazv2.uri}", configuration = {FeignConfig.class})
public interface WsSefazV2FeignClient {

	@PostMapping("/atualizar-certificado")
	Boolean atualizarCertificado(@RequestBody SefazCerficadoForm form);
	
}
