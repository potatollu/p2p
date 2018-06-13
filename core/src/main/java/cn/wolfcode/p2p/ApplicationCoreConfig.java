package cn.wolfcode.p2p;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 核心配置
 */
@MapperScan(basePackages = "cn.wolfcode.p2p.*.mapper")
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling//卡其定时器功能
public class ApplicationCoreConfig {

}
