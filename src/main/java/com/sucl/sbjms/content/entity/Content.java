package com.sucl.sbjms.content.entity;

import com.sucl.sbjms.core.orm.Domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="CONTENT")
public class Content implements Domain {
    @Id
    private String id;

    private String title;

    private boolean personal;//私密

    private boolean original;//原创

    private boolean reprint;//转载

    private boolean top;//置顶

    private boolean commentAble;//可以评论

    private Integer order;

    private String source;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    private String author;

    private Date creationTime;

    @ManyToOne
    @JoinColumn
    private ContentCategory contentCategory;

    @ManyToOne
    @JoinColumn
    private ContentType contentType;
}
