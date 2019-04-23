package com.sucl.sbjms;

import com.sucl.sbjms.core.util.ApplicationContextUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author sucl
 * @date 2019/4/1
 */
@SpringBootApplication
//@EnableJpaRepositories({"com.sucl.sbjms.*.repository"})
//@EntityScan
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication();
        ConfigurableApplicationContext app = application.run(Application.class, args);
        ApplicationContextUtils.setApplicationContext(app);
    }
}
