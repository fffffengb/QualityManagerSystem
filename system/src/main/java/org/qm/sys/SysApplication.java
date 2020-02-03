package org.qm.sys;

import org.qm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

//1.配置springboot的包扫描
@SpringBootApplication(scanBasePackages = {"org.qm.sys", "org.qm.common"})
//2.配置jpa注解的扫描
@EntityScan(value="org.qm.domain")
public class SysApplication {
    /**
     * 启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(SysApplication.class,args);
    }
    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
