package com.itransition.itransitioncoursework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItransitionCourseWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItransitionCourseWorkApplication.class, args);
    }

//    @Bean
//    public ApplicationRunner buildIndex(Indexer indexer) {
//        return (ApplicationArguments args) -> {
//            indexer.indexPersistedData("com.itransition.itransitioncoursework.entity.Item");
//        };
//    }
}
