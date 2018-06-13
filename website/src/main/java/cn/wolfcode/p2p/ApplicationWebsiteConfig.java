package cn.wolfcode.p2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * website配置类
 */
@SpringBootApplication
@PropertySource("classpath:application-website.properties")
@Import(ApplicationCoreConfig.class)
public class ApplicationWebsiteConfig {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationWebsiteConfig.class,args);
    }
}
