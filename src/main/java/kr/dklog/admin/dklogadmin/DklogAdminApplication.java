package kr.dklog.admin.dklogadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DklogAdminApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DklogAdminApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

}
