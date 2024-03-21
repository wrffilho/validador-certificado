package br.am.gov.prodam.validadorcertificado.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class FeignConfig {

	@Value("${ws.sefazv2.bearer.token}")
	private String sefazV2Token;

	@Bean
	public RequestInterceptor requestSefazV2() {
		return requestTemplate -> {
			requestTemplate.header("Authorization", sefazV2Token);
		};
	}

}
