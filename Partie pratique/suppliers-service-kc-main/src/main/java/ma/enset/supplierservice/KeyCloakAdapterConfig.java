package ma.enset.supplierservice;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloakAdapterConfig {

	// prendre la configuration qui existe sur application.properties et non pas 
	// celle qui existe dans Keycloack.json 
	@Bean
	public KeycloakSpringBootConfigResolver springBootConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}
}
