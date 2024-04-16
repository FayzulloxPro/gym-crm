package dev.fayzullokh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "dev.fayzullokh.repositories")
@EntityScan(basePackages = "dev.fayzullokh.entity")
@EnableEurekaClient
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}