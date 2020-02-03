package org.qm.domain.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NoRepositoryBean
public class DataTableBase {
    @Id
    private String id;
    private Double quality;
    private Double work_hour;
    private Date time;
}
