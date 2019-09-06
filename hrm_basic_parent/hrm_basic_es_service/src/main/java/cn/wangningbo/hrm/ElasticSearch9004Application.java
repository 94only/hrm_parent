package cn.wangningbo.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ElasticSearch9004Application {
    public static void main(String[] args) {
        SpringApplication.run(ElasticSearch9004Application.class, args);
    }
}