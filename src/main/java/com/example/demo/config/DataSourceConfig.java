package com.example.demo.config;

import java.util.*;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.beans.factory.annotation.Qualifier;


import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	    basePackages = "com.example.demo.repo",
	    entityManagerFactoryRef = "entityManagerFactory",
	    transactionManagerRef = "transactionManager"
	)
public class DataSourceConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.reader")
	public DataSource readerDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Primary
	@Bean
	public DataSource routingDataSource() {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceContextHolder.DataSourceType.MASTER, masterDataSource());
		targetDataSources.put(DataSourceContextHolder.DataSourceType.READER, readerDataSource());

		RoutingDataSource routingDataSource = new RoutingDataSource();
		routingDataSource.setDefaultTargetDataSource(masterDataSource());
		routingDataSource.setTargetDataSources(targetDataSources);

		return routingDataSource;
	}

	/**
     * 🔴 THIS IS STEP 3
     * Hibernate/JPA WILL USE THIS
     */

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("routingDataSource") DataSource dataSource) {

            LocalContainerEntityManagerFactoryBean emf =
                    new LocalContainerEntityManagerFactoryBean();

            emf.setDataSource(dataSource);
            emf.setPackagesToScan("com.example.demo.entity");
            emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

            return emf;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    
}

