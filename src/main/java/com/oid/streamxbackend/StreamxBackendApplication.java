package com.oid.streamxbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StreamxBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamxBackendApplication.class, args);
    }

}
