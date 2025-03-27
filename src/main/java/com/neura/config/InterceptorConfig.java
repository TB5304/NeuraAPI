package com.neura.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.neura.components.ApiCallInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	
    public final ApiCallInterceptor apiCallInterceptor;

    public InterceptorConfig(ApiCallInterceptor apiCallInterceptor) { 
        this.apiCallInterceptor = apiCallInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiCallInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**");
    }
}
