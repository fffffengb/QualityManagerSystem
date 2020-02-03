package org.qm.domain.data.stat;

import lombok.*;
import org.qm.domain.data.DataTableBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "d_stat_online")
@Data
@NoArgsConstructor
public class DStatOnline extends DataTableBase {
    @Column(name = "stat_id")
    private String statId;
    public DStatOnline(String id, String statId, Double quality, Double work_hour, Date time) {
        super(id, quality, work_hour, time);
        this.statId = statId;
    }
}
