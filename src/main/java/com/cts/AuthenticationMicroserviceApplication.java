package com.cts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.entity.Authority;
import com.cts.entity.User;
import com.cts.repository.UserDetailsRepository;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AuthenticationMicroserviceApplication {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository ;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationMicroserviceApplication.class, args);
	}
	
	@PostConstruct
	protected void init() {
		List<Authority> authorityList = new ArrayList<>();
		
		authorityList.add(createAuthority("USER", "User role"));
		authorityList.add(createAuthority("ADMIN", "Admin role"));
		
		User user = new User();
		user.setFirstName("Sudhanshu");
		user.setLastName("Gupta");
		user.setUserName("sudhanshu2900");
		user.setPassword(passwordEncoder.encode("sid@12345"));
		user.setEnabled(true);
		user.setAuthorities(authorityList);
		
		userDetailsRepository.save(user);
	}
	
	private Authority createAuthority(String roleCode, String roleDetail) {
		Authority authority = new Authority();
		authority.setRoleCode(roleCode);
		authority.setRoleDetail(roleDetail);
		return authority;
	}
	
	/**
	 * It return a prepared Docket instance
	 * select() provide the DocketBuilder object
	 * @return
	 */

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant("/api/v1/*"))
				.apis(RequestHandlerSelectors.basePackage("com.cts")).build().apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfo("Authentication API", "Api for project", "1.0.0","Free API",
				new springfox.documentation.service.Contact("Sudhanshu Gupta", "www.cognizant.com", "2125608@cognizant.com"),"API Licence","https://www.cognizant.com", Collections.emptyList());
	}

}
