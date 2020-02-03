package org.qm.domain.data.group;

import lombok.*;
import org.qm.domain.data.DataTableBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "d_group_daily")
@Data
@NoArgsConstructor
public class DGroupDaily extends DataTableBase {

    private Integer group_id;
    @Builder
    public DGroupDaily(String id, Integer group_id, Double quality, Double work_hour, Date time) {
        super(id, quality, work_hour, time);
        this.group_id = group_id;
    }

}

