package org.qm.domain.conf;

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
@Table(name = "conf_base_inquire")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert()
@DynamicUpdate()
public class ConfBaseInquire {
    @Id
    String id;
    String name;
    String description;
    @Column(name = "default_value")
    String defaultValue;
}
