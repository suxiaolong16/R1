package com.halo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.halo.*.mapper")
public class R1Application {

    public static void main(String[] args) {
        SpringApplication.run(R1Application.class, args);
    }

}
