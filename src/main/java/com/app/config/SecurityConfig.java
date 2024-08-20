package com.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.app.services.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	/*@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(http->{
					http.requestMatchers(HttpMethod.GET, "/auth/get").permitAll();
					
					http.requestMatchers(HttpMethod.GET, "/auth/post").hasAnyAuthority("CREATE","READ");
					http.requestMatchers(HttpMethod.PATCH, "/auth/patch").hasAnyAuthority("DEVELOPER");
					
					http.anyRequest().denyAll();
				})
				.build();
	}
	*/
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetails) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetails);
		
		return provider;
	}
	
	/*@Bean
	public UserDetailsService userDetailsService()
	{
		List<UserDetails> userDetaList = new ArrayList<>();
		
		userDetaList.add(User.withUsername("ronaldo")
				.password("1234")
				.roles("ADMIN")
				.authorities("READ","CREATE")
				.build());
		
		userDetaList.add(User.withUsername("garcia")
				.password("1234")
				.roles("USER")
				.authorities("READ")
				.build());
		
		return new InMemoryUserDetailsManager(userDetaList);
	}
	*/
	
	/*Solo para pruebas
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}*/
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
