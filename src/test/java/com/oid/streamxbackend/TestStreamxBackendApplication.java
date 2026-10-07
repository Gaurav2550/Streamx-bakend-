package com.oid.streamxbackend;

import org.springframework.boot.SpringApplication;

public class TestStreamxBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(StreamxBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
