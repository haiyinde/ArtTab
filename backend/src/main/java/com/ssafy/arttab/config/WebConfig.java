package com.ssafy.arttab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @packageName : com.ssafy.arttab.config
 * @fileName : WebConfig
 * @date : 2022-02-10
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                // PUT DELTE 403 문제 해결
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name()
                );


    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/artworks/**")
//                .addResourceLocations("file:///"+System.getProperty("user.home")+ File.separator+"artwork"+File.separator);
                .addResourceLocations("file:///"+System.getProperty("user.dir")+"artwork"+File.separator);
        registry.addResourceHandler("/profiles/**")
                .addResourceLocations("file:///"+System.getProperty("user.home")+ File.separator+"profile"+File.separator);
        registry.addResourceHandler("/profileDefaultImg/**")
                .addResourceLocations("file:///"+System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator);
    }
}
