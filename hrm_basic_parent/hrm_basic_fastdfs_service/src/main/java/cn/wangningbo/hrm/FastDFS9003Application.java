package cn.wangningbo.hrm;

import cn.wangningbo.hrm.client.FastDFSClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FastDFS9003Application {
    public static void main(String[] args) {
        SpringApplication.run(FastDFS9003Application.class, args);
    }
}