package com.sheepion.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
//@MapperScan(basePackages = "com.sheepion.mapper",factoryBean = MapperFactoryBean.class)
public class MybatisConfig {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    @Bean
    public static DataSource dataSource() {
        System.out.println("配置数据源");
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setBasePackage("com.sheepion.mapper");
        return configurer;
    }

    @Bean
    public static SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 配置 MyBatis
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        //configuration.addMappers("com.sheepion.mapper");
        factoryBean.setConfiguration(configuration);

        // 设置 XML Mapper 文件的路径
        //factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper111/xml/*Mapper.xml"));

        return factoryBean.getObject();
    }

    @Bean
    public static SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Value("${jdbc.driver}")
    public void setDriver(String driver) {
        MybatisConfig.driver = driver;
    }


    @Value("${jdbc.url}")
    public void setUrl(String url) {
        MybatisConfig.url = url;
    }


    @Value("${jdbc.username}")
    public void setUsername(String username) {
        MybatisConfig.username = username;
    }


    @Value("${jdbc.password}")
    public void setPassword(String password) {
        MybatisConfig.password = password;
    }

}
