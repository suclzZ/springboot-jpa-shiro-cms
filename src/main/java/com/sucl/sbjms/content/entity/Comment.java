package com.sucl.sbjms.content.entity;

import com.sucl.sbjms.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

/**
 * 评论
 */
@Data
@Entity
@Table(name="Comment")
public class Comment implements Domain {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @Column(name = "c_id",length = 36)
    private String cId;

    @Column(name = "comment",length = 512)
    @Basic(fetch = FetchType.LAZY)
    private String comment;//评论内容

    @ManyToOne
    @JoinColumn(name = "cont_id")
    private Content content;//管理文章

    @Column(name = "commenter",length = 36)
    private String commenter;//评论人

    @Column(name = "creation_date")
    private Date creationDate;//评论时间

    @Transient
    private String contentTitle;

    public String getContentTitle() {
        return Optional.ofNullable(content).orElseGet(()->{return new Content();}).getTitle();
    }
}
