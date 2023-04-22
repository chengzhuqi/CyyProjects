package com.intrusion.cyy.WebDetectionSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.intrusion.cyy")
public class IntrusionDetectSystemApplication {
    public static void main(String[] args){
        SpringApplication.run(IntrusionDetectSystemApplication.class, args);
    }
}
