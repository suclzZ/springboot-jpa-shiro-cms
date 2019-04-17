package com.sucl.sbjms.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sucl.sbjms.core.orm.Domain;
import com.sucl.sbjms.security.auth.IUser;
import com.sucl.sbjms.security.annotation.Principal;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Temporal
 * @Transient
 *
 * @author sucl
 * @date 2019/4/1
 */
@Data
@Entity
@Table(name = "USER")
@JsonIgnoreProperties({"password"})
public class User implements Domain,IUser {

    @Id
    @Column(name = "user_id",length = 36)
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_code"))
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinTable(name = "user_uperm",
            joinColumns = @JoinColumn(name = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "up_id"))
    private List<Rperm> perms;

    @Principal(order = 1)
    @Column(name = "username",length = 24)
    private String username;

    @Column(name = "password",length = 56)
    private String password;

    @Column(name = "user_caption",length = 56)
    private String userCaption;

    @Column(name = "sex",length = 2)
    private String sex;

    @Column(name = "age",length = 3)
    private String age;

    @Column(name = "birthday",length = 20)
    private String birthday;

    @Principal(order = 2)
    @Column(name = "telephone",length = 16)
    private String telephone;

    @Principal(order = 3)
    @Column(name = "email",length = 56)
    private String email;

    @Column(name = "address",length = 128)
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "description",length = 256)
    private String description;

    @Override
    public Set<String> getRoleIds() {
        if(roles!=null){
            return roles.stream().map(r->r.getRoleCode()).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public String toString() {
        return this.userCaption;
    }
}
