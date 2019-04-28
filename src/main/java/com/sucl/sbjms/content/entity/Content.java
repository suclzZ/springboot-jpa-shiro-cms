package com.sucl.sbjms.content.entity;

import com.sucl.sbjms.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

/**
 * 内容
 */
@Data
@Entity
@Table(name="CONTENT")
public class Content implements Domain {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @Column(name = "id",length = 36)
    private String id;//

    @Column(name = "title",length = 56)
    private String title;//标题

    @Column(name = "description",length = 256)
    private String description;

    @Column(name = "personal",length = 1)
    private boolean personal;//私有

    @Column(name = "original",length = 1)
    private boolean original;//原创

    @Column(name = "reprint",length = 1)
    private boolean reprint;//转载

    @Column(name = "top",length = 1)
    private boolean top;//置顶

    @Column(name = "hot",length = 1)
    private boolean hot;//热门

    @Column(name = "recommend",length = 1)
    private boolean recommend;//推荐

    @Column(name = "comment_able",length = 1)
    private boolean commentAble;//可以评论

    @Column(name = "corder",length = 8)
    private Integer corder;//排序

    @Column(name = "count",length = 8)
    private Integer count;//点击数

    @Column(name = "source",length = 128)
    private String source;//来源

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    private String content;//内容

    @Column(name = "author",length = 56)
    private String author;//作者

    @Column(name = "creation_time")
    private Date creationTime;//创建时间

    @ManyToOne
    @JoinColumn(name = "cc_id")
    private ContentCategory contentCategory;//文章分类

    @ManyToOne
    @JoinColumn(name = "ct_id")
    private ContentType contentType;//内容类型

    @Transient
    private String ctName;
    @Transient
    private String ccName;

    public String getCtName() {
        return Optional.ofNullable(contentType).orElseGet(()->new ContentType()).getCtName();
    }

    public String getCcName() {
        return Optional.ofNullable(contentCategory).orElseGet(()->new ContentCategory()).getCcName();
    }
}
