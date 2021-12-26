package ma.enset.productsapp.web;

import ma.enset.productsapp.repositories.ProductRepository;

import java.security.Principal;
import java.util.HashMap;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.mapping.Map;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;

@Controller
public class ProductController{
	
	@Autowired
	private KeycloakRestTemplate KeycloakRestTemplate;
	
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/products")
    public String products(Model model){
        model.addAttribute("products",productRepository.findAll());
        return "products";
    }
    @GetMapping("/suppliers")
    public String suppliers(Model model){
    	PagedModel<Supplier> pageSuppliers =    KeycloakRestTemplate.getForObject("http://localhost:8083/suppliers",PagedModel.class );
        model.addAttribute("suppliers",pageSuppliers);
    	return "suppliers";
    }
    
    @GetMapping("/jwt")
    @ResponseBody
    public java.util.Map<String,String> map(HttpServletRequest request){
    	KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
    	KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext keycloakSecurityContext = principal.getKeycloakSecurityContext();
        java.util.Map<String,String> map = new HashMap<>();
        map.put("access_token",keycloakSecurityContext.getIdTokenString());
        return map;
    }
}

@Data
class Supplier {
	private Long id;
	private String name;
	private String email;
}