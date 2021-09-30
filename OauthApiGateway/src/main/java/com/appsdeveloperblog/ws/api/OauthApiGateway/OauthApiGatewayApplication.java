package com.appsdeveloperblog.ws.api.OauthApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OauthApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthApiGatewayApplication.class, args);
	}

}
