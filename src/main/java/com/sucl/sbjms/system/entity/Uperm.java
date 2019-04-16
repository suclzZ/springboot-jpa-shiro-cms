package com.sucl.sbjms.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author sucl
 * @date 2019/4/14
 */
@Data
@Entity
@Table(name = "uperm")
public class Uperm {
    @Id
    @Column(name = "up_id",length = 36)
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String upId;

    @Column(name = "permission",length = 128)
    private String permission;
}
