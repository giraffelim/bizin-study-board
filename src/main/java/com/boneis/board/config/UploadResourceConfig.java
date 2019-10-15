package com.boneis.board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class UploadResourceConfig extends WebMvcConfigurerAdapter {

    @Value("${study.upload.config.fileroot}")
    private String uploadResouceLocation;

    @Value("${study.upload.config.prefix}")
    private String webRootDirPrefix;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(webRootDirPrefix + "/**").addResourceLocations(uploadResouceLocation);
        //super.addResourceHandlers(registry);
    }
}
