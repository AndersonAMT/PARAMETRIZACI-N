package com.homerios.msnegociogestioncurso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@ConfigurationPropertiesScan
public class MsNegocioGestionCursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNegocioGestionCursoApplication.class, args);
	}

}
