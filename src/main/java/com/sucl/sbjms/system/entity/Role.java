package com.sucl.sbjms.system.entity;

import com.sucl.sbjms.core.orm.Domain;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/8
 */
@Data
@Entity
@Table(name = "role")
public class Role implements Domain {

    @Id
    @Column(name = "role_code",length = 24)
    private String roleCode;

    @Column(name = "role_name",length = 56)
    private String roleName;

    @Column(name = "description",length = 256)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinTable(name = "role_menu",
            joinColumns = @JoinColumn(name = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "menu_code"))
    private List<Menu> menus;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinTable(name = "role_rperm",
            joinColumns = @JoinColumn(name = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "rp_id"))
    private List<Rperm> perms;
}
