package com.springboot.ysk.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description druid configuration
 * @createDate 2023/7/24 20:28
 * @since 1.0.0
 */
@Configuration
public class DruidConfiguration {
    private final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);

    @Bean("mysqlYskDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.mysql-ysk")
    public DataSource mysqlYskDataSource() {
        logger.info("tip message: 构建 Mysql ysk数据源");
        return DruidDataSourceBuilder.create().build();
    }
}
