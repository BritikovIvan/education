package by.educ.ivan.educationwebappboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath*:Spring-WebappBoot.xml")
public class EducationWebappBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationWebappBootApplication.class, args);
    }

}
