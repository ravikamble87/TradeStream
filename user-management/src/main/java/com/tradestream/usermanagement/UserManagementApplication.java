package com.tradestream.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagementApplication {

    public static void main(String[] args) {
        System.out.println("### DB USER: [" + System.getenv("POSTGRES_USER") + "]");
        System.out.println("### DB PASS: [" + System.getenv("POSTGRES_PASSWORD") + "]");
        SpringApplication.run(UserManagementApplication.class, args);
    }

}
