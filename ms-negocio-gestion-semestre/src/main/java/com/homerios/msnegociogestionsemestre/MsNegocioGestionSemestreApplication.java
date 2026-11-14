package com.homerios.msnegociogestionsemestre;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class MsNegocioGestionSemestreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNegocioGestionSemestreApplication.class, args);
	}

	/**
	 * RestTemplate compartido por las integraciones con curso y docente.
	 *
	 * <p>El tiempo de espera sale del Config Server ({@code integracion.timeout-ms})
	 * en lugar de quedar fijo en el codigo. Se define aca, en la clase de arranque,
	 * para no crear una clase de configuracion con un solo bean.</p>
	 */
	@Bean
	public RestTemplate restTemplate(@Value("${integracion.timeout-ms:3000}") int timeoutMs) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(timeoutMs);
		factory.setReadTimeout(timeoutMs);
		return new RestTemplate(factory);
	}

}
