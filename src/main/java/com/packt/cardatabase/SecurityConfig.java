package com.packt.cardatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.packt.cardatabase.service.UserDetailsServiceImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthenticationFilter authenticationFilter;

	@Autowired
	private AuthEntryPoint exceptionHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	// 必须在WebSecurityConfigurerAdapter子类添加这个方法，把AuthenticationManager注册成一个Bean，LoginController才能使用
	// 否则会报错 Consider defining a bean of type 'org.springframework.security.authentication.AuthenticationManager' in your configuration
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	// 不能空实现这个方法，否则所有端口都不会要求验证
	// POST method request to the /login endpoint is allowed 
	// without authentication and that requests to all other endpoints require 
	// authentication. We will also define that Spring Security will never create a session, 
	// and therefore we can also disable csrf
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests()
				// POST request to /login endpoint is not secured
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				// All other requests are secured
				.anyRequest().authenticated()
				// 处理无权限的异常
				.and().exceptionHandling().authenticationEntryPoint(exceptionHandler)
				// 增加验证过滤器
				.and().addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	// 处理跨域问题
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		//config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(false);
		config.applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	
//	//如果不使用数据库的用户密码，使用默认网页提供的输入框，就用这个方法
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//	 return new InMemoryUserDetailsManager(user);
//	}
}
