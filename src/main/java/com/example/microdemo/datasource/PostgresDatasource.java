package com.example.microdemo.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgresDatasource {

    @Bean //instantiate this class as a bean
    @ConfigurationProperties("app.datasource") //create configuration file that will contain the username, url, password etc.
    public HikariDataSource hikariDataSource(){
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }
}
