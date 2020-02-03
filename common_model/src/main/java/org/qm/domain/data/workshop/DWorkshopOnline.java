package org.qm.domain.data.workshop;

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
@Table(name = "d_workshop_online")
@Data
@NoArgsConstructor
public class DWorkshopOnline extends DataTableBase {
    @Column(name = "workshop_id")
    private String workshopId;
    public DWorkshopOnline(String id, String workshopId, Double quality, Double work_hour, Date time) {
        super(id, quality, work_hour, time);
        this.workshopId = workshopId;
    }
}
