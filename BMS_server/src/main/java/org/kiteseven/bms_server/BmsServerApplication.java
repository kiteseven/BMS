package org.kiteseven.bms_server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "org.kiteseven")
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
public class BmsServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BmsServerApplication.class, args);
    }

}
