package com.sucl.sbjms.content.entity;

import com.sucl.sbjms.core.orm.Domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 站在文档的角度进行分类
 */
@Entity
@Table(name="Content_Category")
public class ContentCategory implements Domain {
}
