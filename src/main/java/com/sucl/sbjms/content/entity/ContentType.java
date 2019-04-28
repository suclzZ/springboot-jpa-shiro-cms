package com.sucl.sbjms.content.entity;

import com.sucl.sbjms.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 站在内容的角度进行分类
 */
@Data
@Entity
@Table(name="Content_Type")
public class ContentType implements Domain {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @Column(name = "ct_Id",length = 36)
    private String ctId;

    @Column(name = "parent_Id",length = 36)
    private String parentId;

    @Column(name = "ct_code",length = 12)
    private String ctCode;

    @Column(name = "ct_name",length = 56)
    private String ctName;

    @Transient
    private String parentName;

}
