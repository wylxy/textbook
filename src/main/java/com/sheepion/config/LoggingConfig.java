package com.sheepion.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {
    @Bean
    public static LoggerContext getLoggerContext(){
        System.out.println("LoggingConfig#getLoggerContext");
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }
    @Bean
    public static Logger getAppLogger(LoggerContext loggerContext){
        System.out.println("LoggingConfig#getAppLogger");
        //为com.sheepion包下的类设置日志等级为DEBUG
        Logger logger = loggerContext.getLogger("com.sheepion");
        logger.setLevel(ch.qos.logback.classic.Level.DEBUG);
        return logger;
    }

    @Bean
    public static Logger getDefaultLogger(LoggerContext loggerContext) {
        System.out.println("LoggingConfig#getDefaultLogger");
        //为其他包下的类设置日志等级为INFO
        Logger logger = loggerContext.getLogger("root");
        logger.setLevel(ch.qos.logback.classic.Level.INFO);
        return logger;
    }
}
