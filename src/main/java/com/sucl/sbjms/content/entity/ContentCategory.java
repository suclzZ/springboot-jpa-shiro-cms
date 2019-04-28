package com.sucl.sbjms.content.entity;

import com.sucl.sbjms.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 站在文档的角度进行分类
 */
@Data
@Entity
@Table(name="Content_Category")
public class ContentCategory implements Domain {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "cc_id",length = 36)
    private String ccId;

    @Column(name = "cc_name",length = 36)
    private String ccName;

    @Column(name = "cc_code",length = 24)
    private String ccCode;

    @Column(name = "cc_group",length = 2)
    private String ccGroup;
}
