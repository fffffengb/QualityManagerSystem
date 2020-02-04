package org.qm.domain.conf;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "conf_user_inquire")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert()
@DynamicUpdate()
public class ConfUserInquire {
    @Id
    String id;
    String value;
    String conf_id;
    String employee_id;
}
