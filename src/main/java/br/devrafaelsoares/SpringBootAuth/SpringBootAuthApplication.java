package br.devrafaelsoares.SpringBootAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableJpaAuditing
public class SpringBootAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAuthApplication.class, args);
	}

}
