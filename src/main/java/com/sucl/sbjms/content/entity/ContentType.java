package com.sucl.sbjms.content.entity;

import com.sucl.sbjms.core.orm.Domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 站在内容的角度进行分类
 */
@Entity
@Table(name="Content_Type")
public class ContentType implements Domain {
    private String ctId;
    private String name;
    private String code;
    private String creator;
}
