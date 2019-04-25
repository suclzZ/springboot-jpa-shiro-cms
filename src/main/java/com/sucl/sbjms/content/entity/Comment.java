package com.sucl.sbjms.content.entity;

import com.sucl.sbjms.core.orm.Domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 评论
 */
@Entity
@Table(name="Comment")
public class Comment implements Domain {
}
