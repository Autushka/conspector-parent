package com.config.impl;

import com.config.IDataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by aautushk on 9/12/2015.
 */
@Component
@Profile("production")
@Configuration
public class ProdDataSourceConfig implements IDataSourceConfig {
    @Bean
    public DataSource setup(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/gateway");
        ds.setUsername("root");
        ds.setPassword("");

        return ds;
    }
}
