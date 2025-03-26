package com.introducao.spring_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // colocado quando utilizamos o listener em alguma entidade
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}


}
