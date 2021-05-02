package by.educ.ivan.educationwebappboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath*:Spring-WebappBoot.xml")
@EntityScan(basePackages = "by.educ.ivan.education.model")
public class EducationWebappBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationWebappBootApplication.class, args);
    }

}
