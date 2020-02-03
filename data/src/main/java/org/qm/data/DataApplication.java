package org.qm.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//1.配置springboot的包扫描
@SpringBootApplication(scanBasePackages = {"org.qm.data", "org.qm.common"})
//2.配置jpa注解的扫描
@EntityScan(value="org.qm.domain")

public class DataApplication {
    /**
     * 启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class,args);
    }

}
