package org.qm.domain.data.group;

import lombok.*;
import org.qm.domain.data.DataTableBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "d_group_daily")
@Data
@NoArgsConstructor
public class DGroupDaily extends DataTableBase {

    @Column(name = "group_id")
    private String groupId;
    @Builder
    public DGroupDaily(String id, String groupId, Double quality, Double work_hour, Date time) {
        super(id, quality, work_hour, time);
        this.groupId = groupId;
    }

}

