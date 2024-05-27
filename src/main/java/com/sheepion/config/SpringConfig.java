package com.sheepion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import({
        LoggingConfig.class,
        ServletConfig.class,
        MybatisConfig.class,
        SwaggerConfig.class,
})
@ComponentScan(value = {
        "com.sheepion.config",
        "com.sheepion.filter",
        "com.sheepion.interceptor",
        "com.sheepion.aop",
        "com.sheepion.service",
        "com.sheepion.model",
        "com.sheepion.controller",
})
@PropertySources({
        @PropertySource("classpath:application.properties")
})
//@Import({JdbcConfig.class, MybatisConfig.class})
//@EnableTransactionManagement
public class SpringConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
