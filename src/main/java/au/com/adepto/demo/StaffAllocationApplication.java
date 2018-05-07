package au.com.adepto.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class StaffAllocationApplication {

    public static void main(final String[] args) {
        SpringApplication.run(StaffAllocationApplication.class, args);
    }
}
