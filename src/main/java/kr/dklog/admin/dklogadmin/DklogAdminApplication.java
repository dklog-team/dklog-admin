package kr.dklog.admin.dklogadmin;

import kr.dklog.admin.dklogadmin.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(AppConfig.class)
@EnableJpaAuditing
@SpringBootApplication
public class DklogAdminApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DklogAdminApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

}
