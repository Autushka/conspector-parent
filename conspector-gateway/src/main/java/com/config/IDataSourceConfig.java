package com.config;

import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Created by aautushk on 9/12/2015.
 */

public interface IDataSourceConfig {
    DataSource setup();
}
