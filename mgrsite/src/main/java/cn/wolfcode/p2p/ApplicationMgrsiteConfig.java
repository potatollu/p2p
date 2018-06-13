package cn.wolfcode.p2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * mgr配置类
 */
@SpringBootApplication
@PropertySource("classpath:application-mgrsite.properties")
@Import(ApplicationCoreConfig.class)
public class ApplicationMgrsiteConfig {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMgrsiteConfig.class,args);
    }

}
