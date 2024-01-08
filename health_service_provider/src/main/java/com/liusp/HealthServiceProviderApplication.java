package com.liusp;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.liusp.dao")
public class HealthServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthServiceProviderApplication.class, args);
    }

}
