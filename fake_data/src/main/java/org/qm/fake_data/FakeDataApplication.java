package org.qm.fake_data;

import org.qm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

//1.配置springboot的包扫描
@SpringBootApplication(scanBasePackages = {"org.qm.fake_data", "org.qm.common"})
//2.配置jpa注解的扫描
@EntityScan(value="org.qm.domain")
//3.开启定时任务注解
@EnableScheduling
public class FakeDataApplication {
    /**
     * 启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(FakeDataApplication.class,args);
    }
    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
