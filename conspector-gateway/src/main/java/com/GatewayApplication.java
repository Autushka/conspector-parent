package com;

import java.security.Principal;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.
		authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration // needed to specify that the class contains global spring configurations
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories // needed to enable JPA based repositories
@EnableTransactionManagement
@RestController // needed to enable rest end points within the class
@EnableRedisHttpSession // needed for cross application authentication
@EnableGlobalMethodSecurity(securedEnabled=true) // needed for method based security (@Secured("ROLE_ADMIN") annotation))
public class GatewayApplication {
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/gateway");
		ds.setUsername("root");
		ds.setPassword("");

		return ds;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		return adapter;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setShowSql(true);
		vendorAdapter.setGenerateDdl(false); //true value not for production !!! update db after entityManager instantiation based on entities
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.entity");
		factory.setDataSource(dataSource());

		factory.afterPropertiesSet();

		return factory.getObject();
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//2419200 means 4 weeks
			http
					.formLogin()
				.and()
					.rememberMe()
					.tokenValiditySeconds(2419200)
					.key("conspector")
					.and()
					.logout()
				.and()
					.authorizeRequests()
						.antMatchers("/#/login", "/logout", "/user", "/login?logout", "/", "/index.html", "/build/**", "/views/**", "/img/**", "/fonts/**", "/gateway/createUser").permitAll()
						.anyRequest().authenticated()
					.and()
					.csrf().disable();
		}

		@Autowired
		private DataSource dataSource;

		@Override
		protected void configure(AuthenticationManagerBuilder auth)	throws Exception {
			auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new StandardPasswordEncoder("53cr3t"));

			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			populator.setContinueOnError(true);
			populator.addScript(new ClassPathResource("/sql/db_initializer.sql"));
			populator.populate(dataSource.getConnection());
		}
	}
}