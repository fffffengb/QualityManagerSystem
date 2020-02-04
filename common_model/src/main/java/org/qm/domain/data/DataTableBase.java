package org.qm.domain.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.Column;
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
    @Column(name = "work_hour")
    private Double workHour;
    private Date time;
}
