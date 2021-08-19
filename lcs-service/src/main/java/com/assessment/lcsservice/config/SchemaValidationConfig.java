package com.assessment.lcsservice.config;

import com.assessment.lcsservice.config.SchemaValidationResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SchemaValidationConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SchemaValidationResolver(objectMapper, resourcePatternResolver));
    }
}
