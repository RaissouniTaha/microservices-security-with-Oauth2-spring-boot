package com.appsdeveloperblog.ws.api.ResourceServer.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
		
		
		http
//		.cors().and()
		.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/users/status/check")
			//.hasAuthority("SCOPE_profile")
			.hasRole("developer")
			//.hasAnyRole("developer", "user")
		.anyRequest().authenticated()
		.and()
		.oauth2ResourceServer()
		.jwt()
		.jwtAuthenticationConverter(jwtAuthenticationConverter);


	}
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
//		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST"));
//		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
//
//		org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		return source;
//	}

}
