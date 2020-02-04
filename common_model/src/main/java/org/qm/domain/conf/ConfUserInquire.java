package org.qm.domain.conf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "conf_user_inquire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert()
@DynamicUpdate()
public class ConfUserInquire {
    @Id
    String id;
    String value;
    @Column(name = "conf_id")
    String confId;
    @Column(name = "employee_id")
    String employeeId;
}
