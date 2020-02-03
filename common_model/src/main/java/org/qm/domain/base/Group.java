package org.qm.domain.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bs_group")
@Data
@NoArgsConstructor
public class Group extends BaseTableBase{

//    Integer employee_id;
//
//    public Group(String id, String name, Integer employee_id) {
//        super(id, name);
//        this.employee_id = employee_id;
//    }
}
