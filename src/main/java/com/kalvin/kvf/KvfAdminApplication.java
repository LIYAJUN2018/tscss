package com.kalvin.kvf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
//@MapperScan("com.kalvin.kvf.**.*")
public class KvfAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(KvfAdminApplication.class, args);
    }

}
