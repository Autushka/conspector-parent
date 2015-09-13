package com;

import javax.persistence.EntityManagerFactory;

import com.config.IDataSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.data.auditing.DateTimeProvider;

@Configuration // needed to specify that the class contains global spring configurations
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories // needed to enable JPA based repositories
@EnableTransactionManagement
@RestController // needed to enable rest end points within the class
@EnableRedisHttpSession // needed for cross application authentication
@EnableGlobalMethodSecurity(securedEnabled=true) // needed for method based security (@Secured("ROLE_ADMIN") annotation))
@EnableJpaAuditing//needed to activate auditing(automatically managed fields createdBy, createdDate, lastModifiedBy, lastModifiedDate)
public class GatewayApplication {
	@Autowired
	private IDataSourceConfig dataSourceConfig;

	private class SpringSecurityAuditorAware implements AuditorAware<String> {
		@Override
		public String getCurrentAuditor() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.isAuthenticated()) {
				return null;
			}

			return authentication.getName();
			//return ((User) authentication.getPrincipal()).getUsername();
		}
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new SpringSecurityAuditorAware();
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
		factory.setDataSource(dataSourceConfig.setup());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
		cookieLocaleResolver.setCookieName("conspectorLanguage");
		cookieLocaleResolver.setCookieMaxAge(604800);//one month
		return cookieLocaleResolver;
	}

	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages");
		// if true, the key of the message will be displayed if the key is not
		// found, instead of throwing a NoSuchMessageException
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		// # -1 : never reload, 0 always reload
		messageSource.setCacheSeconds(0);
		return messageSource;
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
		private IDataSourceConfig dataSourceConfig;

		@Override
		protected void configure(AuthenticationManagerBuilder auth)	throws Exception {
			auth.jdbcAuthentication().dataSource(dataSourceConfig.setup()).passwordEncoder(new StandardPasswordEncoder("53cr3t"));

			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			populator.setContinueOnError(true);
			populator.addScript(new ClassPathResource("/sql/db_initializer.sql"));
			populator.populate(dataSourceConfig.setup().getConnection());
		}
	}
}