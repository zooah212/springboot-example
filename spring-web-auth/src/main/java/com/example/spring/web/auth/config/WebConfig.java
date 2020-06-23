package com.example.spring.web.auth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.example.spring.common.cache.ICacheService;
import com.example.spring.web.core.aop.LogAop;
import com.example.spring.web.core.cache.RedisCacheServiceImpl;
import com.example.spring.web.core.interceptor.LocaleChangeInterceptor;
import com.example.spring.web.core.json.Jackson2HttpMessageConverter;

/**
 * @author huss
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;

    /**
     * 请求日志打印
     * 
     * @return
     */
    @Bean
    public LogAop logAop() {
        return new LogAop();
    }

    /**
     * redis缓存
     * 
     * @return
     */
    @Bean
    public ICacheService redisCacheService() {
        return new RedisCacheServiceImpl();
    }

    /**
     * 跨域
     * 
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowCredentials(true).allowedHeaders("*").allowedOrigins("*").allowedMethods("*");
    }

    /**
     * 拦截器
     * 
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 国际化
        registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2HttpMessageConverter jackson2HttpMessageConverter = new Jackson2HttpMessageConverter();
        converters.add(jackson2HttpMessageConverter);
    }

    /**
     * 配置静态资源
     * 
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /**
     * 防XSS注入
     *
     * @return FilterRegistrationBean
     */
    // @Bean
    // public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
    // FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>(new XssFilter());
    // registration.addUrlPatterns("/*");
    // registration.setName("xssFilter");
    // return registration;
    // }

}