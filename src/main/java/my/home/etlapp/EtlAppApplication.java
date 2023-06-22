package my.home.etlapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EtlAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtlAppApplication.class, args);
    }

}
