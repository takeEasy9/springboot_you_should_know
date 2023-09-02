package com.springboot.ysk.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description mysql database configuration
 * @createDate 2023/7/24 20:44
 * @since 1.0.0
 */
@Configuration
@EnableTransactionManagement
public class MysqlYskConfiguration {

    @Bean("mysqlYskSqlSessionFactory")
    public SqlSessionFactory mysqlYskSqlSessionFactory(@Qualifier("mysqlYskDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:./mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("mysqlYskTransactionManager")
    public TransactionManager mysqlYskTransactionManager(@Qualifier("mysqlYskDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(value = "mysqlSqlSessionTemplate")
    public SqlSessionTemplate mysqlYskSqlSessionTemplate(@Qualifier("mysqlYskSqlSessionFactory") SqlSessionFactory mysqlYskSqlSessionFactory) {
        return new SqlSessionTemplate(mysqlYskSqlSessionFactory);
    }

    @Bean("mysqlTransactionTemplate")
    public TransactionTemplate mysqlYskTransactionTemplate(@Qualifier("mysqlYskTransactionManager") TransactionManager transactionManager) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return new TransactionTemplate((PlatformTransactionManager) transactionManager, transactionDefinition);
    }
}
