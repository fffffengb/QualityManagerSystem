package org.qm.domain.data.stat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.qm.domain.data.DataTableBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "d_stat_avg")
@Data
@NoArgsConstructor
public class DStatAvg extends DataTableBase {
    @Column(name = "stat_id")
    private String statId;
    public DStatAvg(String id, String statId, Double quality, Double work_hour, Date time) {
        super(id, quality, work_hour, time);
        this.statId = statId;
    }
}
