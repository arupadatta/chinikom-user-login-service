package com.chinikom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
// @SuppressWarnings("deprecation")
@EnableAutoConfiguration
// Sprint Boot Automatic Configuration
@ComponentScan(basePackages = "com.chinikom")
@EnableJpaRepositories("com.chinikom.dao.jpa")
// To segregate MongoDB and JPA repositories. Otherwise not needed.
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
public class UserLoginService {
	static Class<UserLoginService> applicationClass = UserLoginService.class;

	public static void main(String[] args) {
		SpringApplication.run(UserLoginService.class, args);
	}
}
