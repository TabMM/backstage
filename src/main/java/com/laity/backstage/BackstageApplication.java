package com.laity.backstage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.laity.backstage.*.dao")
@SpringBootApplication
public class BackstageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackstageApplication.class, args);
    }

}
