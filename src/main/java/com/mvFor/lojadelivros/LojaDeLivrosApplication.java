package com.mvFor.lojadelivros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mvFor.lojadelivros.config.property.LojaApiProperty;

@EnableConfigurationProperties(LojaApiProperty.class)
@SpringBootApplication
public class LojaDeLivrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaDeLivrosApplication.class, args);
	}

}
