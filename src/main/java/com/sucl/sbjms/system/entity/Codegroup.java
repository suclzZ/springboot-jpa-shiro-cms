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
@Table(name = "codegroup")
public class Codegroup implements Domain {

    @Id
    @Column(name = "group_id",length = 36)
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String groupId;

    @Column(name = "group_name",length = 56)
    private String groupName;

    @Column(name = "group_type",length = 26)
    private String groupType;

    @Column(name = "group_caption",length = 128)
    private String groupCaption;

}
