package org.qm.domain.data.group;

import lombok.*;
import org.qm.domain.data.DataTableBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "d_group_online")
@Data
@NoArgsConstructor
public class DGroupOnline extends DataTableBase {
    @Column(name = "group_id")
    private String groupId;
    @Builder
    public DGroupOnline(String id, String groupId, Double quality, Double work_hour, Date time) {
        super(id, quality, work_hour, time);
        this.groupId = groupId;
    }

}
