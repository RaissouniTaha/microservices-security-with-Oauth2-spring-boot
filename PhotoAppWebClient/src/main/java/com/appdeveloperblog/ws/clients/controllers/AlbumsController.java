package com.appdeveloperblog.ws.clients.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.appdeveloperblog.ws.clients.model.AlbumRest;

@Controller
public class AlbumsController {

	@Autowired
	 OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
	
//	@Autowired
//	 RestTemplate restTemplate;
	@Autowired
	WebClient webClient;
	
	
	@GetMapping("/albums")
	public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
//		OAuth2AuthorizedClient oauth2Client = oAuth2AuthorizedClientService.loadAuthorizedClient(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
//														   oAuth2AuthenticationToken.getName());
//		String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();		
//		System.out.println("jwtAccessToken = "+jwtAccessToken);
		
//		System.out.println("Principal = "+principal);
//		OidcIdToken idToken = principal.getIdToken();
//		String idTokenValue = idToken.getTokenValue();
//		System.out.println("idTokenValue = "+idTokenValue);
		
		 String url = "http://localhost:8082/albums";
//		 HttpHeaders headers = new HttpHeaders();
//		 headers.add("Authorization", "Bearer "+jwtAccessToken);
//		 HttpEntity<AlbumRest> entity = new HttpEntity<>(headers);
		
//		ResponseEntity<List<AlbumRest>> responseEntity =  restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AlbumRest>>() {});
		
		 List<AlbumRest> albums = webClient.get()
				 						   .uri(url)
				 						   .retrieve()
				 			.bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>(){})
				 						   .block();
//				 responseEntity.getBody();
		
		
//		AlbumRest album1 = new AlbumRest();
//		album1.setAlbumId(UUID.randomUUID().toString());
//		album1.setAlbumTitle("Title of album one");
//		album1.setAlbumUrl("http://localhost:8082/albums/1");
//		
//		AlbumRest album2 = new AlbumRest();
//		album2.setAlbumId(UUID.randomUUID().toString());
//		album2.setAlbumTitle("Title of album two");
//		album2.setAlbumUrl("http://localhost:8082/albums/2");
//		
		model.addAttribute("albums", albums);
		
		return "albums";	
	}
	
}
