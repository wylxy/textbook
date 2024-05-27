package com.sheepion.config;

import com.sheepion.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@ComponentScan({"com.sheepion.controller","com.sheepion.config"})
//@Import({SpringMvcSupport.class})
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {
    @Autowired
    private PermissionInterceptor permissionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //.allowedOrigins("*") //允许跨域的域名，可以用*表示允许任何域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS") //允许的方法
                .allowedHeaders("*") //允许的请求头
                .allowCredentials(true) //是否允许证书 不再默认开启
                .maxAge(3600) //预检请求的有效期
                .allowedOriginPatterns("*"); //允许的跨域模式
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("添加swagger-ui处理");
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}
