package com.sucl.sbjms.system.entity;

import com.sucl.sbjms.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author sucl
 * @date 2019/4/16
 */
@Data
@Entity
@Table(name = "perm")
public class Perm implements Domain {

    @Id
    @Column(name = "perm_id",length = 36)
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String permId;

    @Column(name = "type",length = 2)
    private String type;

    @Column(name = "type_id",length = 36)
    private String typeId;

    @Column(name = "domain",length = 24)
    private String domain;

    @Column(name = "action",length = 56)
    private String action;

    @Column(name = "target",length = 36)
    private String target;

}
