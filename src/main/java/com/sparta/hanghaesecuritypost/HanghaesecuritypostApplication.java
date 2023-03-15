package com.sparta.hanghaesecuritypost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // TimeStamped을 사용할때 필요한 어노테이션
@SpringBootApplication
public class HanghaesecuritypostApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaesecuritypostApplication.class, args);
    }

}
