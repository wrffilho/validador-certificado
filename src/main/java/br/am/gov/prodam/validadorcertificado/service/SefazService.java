package br.am.gov.prodam.validadorcertificado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.am.gov.prodam.validadorcertificado.config.WsSefazV2FeignClient;
import br.am.gov.prodam.validadorcertificado.form.SefazCerficadoForm;

@Service
public class SefazService {
	
	@Autowired
    private WsSefazV2FeignClient sefazV2FeignClient;
	
	
	public void atualizarCertificado() {
		String s = "Wanderlei Rocha Filho";
		
		byte[] bytes = s.getBytes();
		
		SefazCerficadoForm form = new SefazCerficadoForm();
		form.setCertificado(bytes);
		
		sefazV2FeignClient.atualizarCertificado(form);
	}

}
