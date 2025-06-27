package com.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardMain6001 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardMain6001.class,args);
    }
}