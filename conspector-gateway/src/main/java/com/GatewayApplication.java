package com;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.
		authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
@EnableZuulProxy
@EnableRedisHttpSession
public class GatewayApplication {
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.httpBasic()
			.and()
				.logout()
			.and()
				.authorizeRequests()
					.antMatchers("/#/login", "/logout", "/user", "/login?logout", "/", "/index.html", "/build/**", "/views/**", "/img/**", "/fonts/**").permitAll()
					.anyRequest().authenticated()
			.and()
			.csrf().disable();
			// @formatter:on
		}


		@Override
		protected void configure(AuthenticationManagerBuilder auth)
				throws Exception {
			auth
					.inMemoryAuthentication()
					.withUser("user").password("password").roles("USER").and()
					.withUser("admin").password("password").roles("ADMIN");
		}

//		@Bean
//		public DataSource dataSource() {
//			DriverManagerDataSource ds = new DriverManagerDataSource();
//			ds.setDriverClassName("com.mysql.jdbc.Driver");
//			ds.setUrl("jdbc:mysql://localhost/gateway");
//			ds.setUsername("root");
//			ds.setPassword("");
//			return ds;
//		}
//
//		@Autowired
//		DataSource dataSource;
//		@Override
//		protected void configure(AuthenticationManagerBuilder auth)
//				throws Exception {
//			auth
//					.jdbcAuthentication()
//					.dataSource(dataSource);
//		}

	}

}

//sql scripts to populate db. Note: varchar_ignorecase was causing an issue and was changed to just varchar...

//create table users(
//		username varchar_ignorecase(50) not null primary key,
//		password varchar_ignorecase(50) not null,
//		enabled boolean not null);
//
//		create table authorities (
//		username varchar_ignorecase(50) not null,
//		authority varchar_ignorecase(50) not null,
//		constraint fk_authorities_users foreign key(username) references users(username));
//		create unique index ix_auth_username on authorities (username,authority);
