package com.sucl.sbjms.system.entity;

import com.sucl.sbjms.core.orm.Domain;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sucl
 * @date 2019/4/8
 */
@Data
@Entity
@Table(name = "menu")
public class Menu implements Domain {
    @Id
    @Column(name = "menu_code",length = 24)
    private String menuCode;

    @Column(name = "menu_name",length = 56)
    private String menuName;

    @Column(name = "parent_menu_code",length = 24)
    private String parentMenuCode;

    @Column(name = "style",length = 56)
    private String style;

    @Column(name = "leaf",length = 1)
    private String leaf;

    @Column(name = "path",length = 128)
    private String path;
}
