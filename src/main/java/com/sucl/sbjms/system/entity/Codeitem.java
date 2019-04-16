package com.sucl.sbjms.system.entity;

import com.sucl.sbjms.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author sucl
 * @date 2019/4/8
 */
@Data
@Entity
@Table(name = "codeitem")
public class Codeitem implements Domain {

    @Id
    @Column(name = "item_id",length = 36)
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String itemId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Codegroup codegroup;

    @Column(name = "item_code",length = 12)
    private String itemCode;

    @Column(name = "item_name",length = 56)
    private String itemName;

    @Column(name = "item_type",length = 2)
    private String itemType;
}
