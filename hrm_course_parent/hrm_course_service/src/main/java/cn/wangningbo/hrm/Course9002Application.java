package cn.wangningbo.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.wangningbo.hrm.mapper")
@EnableFeignClients
public class Course9002Application {
    public static void main(String[] args) {
        SpringApplication.run(Course9002Application.class, args);
    }
}
