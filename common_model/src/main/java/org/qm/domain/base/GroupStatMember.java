package org.qm.domain.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "bs_group_stat_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupStatMember {
    @Id
    String id;
    @Column(name = "stat_id")
    String statId;
    @Column(name = "morning_shift_id")
    String morningShiftId;
    @Column(name = "middle_shift_id")
    String middleShiftId;
    @Column(name = "night_shift_id")
    String nightShiftId;
    @Column(name = "update_time")
    Date updateTime;
    @Column(name = "group_id")
    String groupId;
}
