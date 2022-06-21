package com.itransition.itransitioncoursework.config;
//Sevinch Abdisattorova 06/16/2022 9:57 AM


import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Value("${CLOUD_NAME}")
    private String cloudName;

    @Value("${CLOUD_API_KEY}")
    private String apiSecret;

    @Value("${CLOUD_API_KEY}")
    private String apiKey;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }


    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
