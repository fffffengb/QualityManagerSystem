package org.qm.fake_data.fake_data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "base-qm-cfg")
@Data
public class BaseQmCfg {
    public Integer stat_num;
    public Integer group_num;
    public Integer workshop_num;
}
