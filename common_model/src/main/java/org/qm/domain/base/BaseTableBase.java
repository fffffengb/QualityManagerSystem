package org.qm.domain.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NoRepositoryBean
public class BaseTableBase {
    @Id
    String id;
    String name;
    @Column(name = "employee_id")
    String employeeId;
}
