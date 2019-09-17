package cn.wangningbo.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("cn.wangningbo.hrm.mapper")
public class User9008Application {
    public static void main(String[] args) {
        SpringApplication.run(User9008Application.class, args);
    }
}