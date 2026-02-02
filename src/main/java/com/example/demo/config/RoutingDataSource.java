package com.example.demo.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource{

	@Override
    protected Object determineCurrentLookupKey() {
        DataSourceContextHolder.DataSourceType type = DataSourceContextHolder.get();
        return (type == null) ? DataSourceContextHolder.DataSourceType.MASTER : type;
    }
	
}
