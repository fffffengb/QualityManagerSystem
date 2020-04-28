package org.qm.domain.base;

import com.sun.tracing.dtrace.ArgsAttributes;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "bs_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "id")
    int Id;
    String name;
    String sex;
    Date birthday;
    String birthplace;
}
