package com.appsdeveloperblog.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.UserCredentialStore;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

public class RemoteUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {

	private KeycloakSession session;
	private ComponentModel model;
	private UserApiService userService;
	
	
	public RemoteUserStorageProvider(KeycloakSession session, ComponentModel model, UserApiService userService) {
		this.session = session;
		this.model = model;
		this.userService = userService;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {

		UserModel returnedValue = null;
		
		User user = userService.getUserDetails(username);
		if(user != null) {
			returnedValue = createUserModel(username, realm);
		}
		
		return returnedValue;
	}

	private UserModel createUserModel(String username, RealmModel realm) {
		
		return new AbstractUserAdapter(session, realm, model) {
			@Override
			public String getUsername() {
				return username;
			}
		};
		
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
		
		return PasswordCredentialModel.TYPE.equals(credentialType);
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		if (!supportsCredentialType(credentialType)) {
			return false;
		}
		return !getCredentialStore().getStoredCredentialsByType(realm, user, credentialType).isEmpty();
	}

	private UserCredentialStore getCredentialStore() {
		return session.userCredentialManager();
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
		VerifyPasswordResponse verifyPasswordResponse = userService.verifyUserPassword(user.getUsername(), credentialInput.getChallengeResponse());
		if(verifyPasswordResponse == null) {
			return false;
		}
		return verifyPasswordResponse.getResult();
	}

}
