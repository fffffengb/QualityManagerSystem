package org.qm.domain.data.stat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.qm.domain.data.DataTableBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "d_stat_daily")
@Data
@NoArgsConstructor

public class DStatDaily extends DataTableBase {
    private Integer stat_id;
    public DStatDaily(String id, Integer stat_id, Double quality, Double work_hour, Date time) {
        super(id, quality, work_hour, time);
        this.stat_id = stat_id;
    }
}
