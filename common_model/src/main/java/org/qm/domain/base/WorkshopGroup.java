package org.qm.domain.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bs_workshop_group")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopGroup {
    @Id
    String id;
    @Column(name = "workshop_id")
    String workshopId;
    @Column(name = "group_id")
    String groupId;
}
