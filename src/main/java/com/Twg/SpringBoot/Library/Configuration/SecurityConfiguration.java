package com.Twg.SpringBoot.Library.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Twg.SpringBoot.Library.JotModel.JwtFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration 
{
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
    private JwtFilter jwtFilter;
    public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}


	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain authorizeHttpRequests(HttpSecurity httpSecurity)
	{
    	httpSecurity
    	.authorizeHttpRequests(
    			(auth)->
    			auth
    			.requestMatchers("/auth/**","/swagger-ui/**","/v3/api-docs/**").permitAll()
    			.anyRequest()
    			.authenticated()
    			).addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
    	.csrf((csrf)->csrf.disable());
	
	
	    return httpSecurity.build();
	}
   
    @Bean
   
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder)
    {
    	
    	DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider(userDetailsService);
    	
    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    	
    	return new ProviderManager(daoAuthenticationProvider);
    	
    }
    
   @Bean
   public PasswordEncoder passwordEncoder()
   {
	   return new BCryptPasswordEncoder();
   }
	
}

