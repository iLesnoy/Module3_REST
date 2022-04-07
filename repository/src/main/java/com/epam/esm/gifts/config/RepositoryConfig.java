package com.epam.esm.gifts.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm.gifts")
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement
public class RepositoryConfig {

        @Value("${db.driver}")
        private String driverClassName;
        @Value("${db.url}")
        private String dbUrl;
        @Value("${user}")
        private String dbUsername;
        @Value("${password}")
        private String dbPassword;
        @Value("${pool.size}")
        private int poolSize;

        @Bean
        public DataSource dataSource() {
            HikariDataSource hikariDataSource = new HikariDataSource();
            hikariDataSource.setDriverClassName(driverClassName);
            hikariDataSource.setJdbcUrl(dbUrl);
            hikariDataSource.setUsername(dbUsername);
            hikariDataSource.setPassword(dbPassword);
            hikariDataSource.setMaximumPoolSize(poolSize);
            return hikariDataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }
