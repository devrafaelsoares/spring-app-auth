package br.devrafaelsoares.SpringBootAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class SpringBootAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAuthApplication.class, args);
	}

}
