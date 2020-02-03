package org.qm.domain.data.workshop;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.qm.domain.data.DataTableBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "d_workshop_daily")
@Data
@NoArgsConstructor
public class DWorkshopDaily extends DataTableBase {
    private Integer workshop_id;
    public DWorkshopDaily(String id, Integer workshop_id, Double quality, Double work_hour, Date time) {
        super(id, quality, work_hour, time);
        this.workshop_id = workshop_id;
    }
}
