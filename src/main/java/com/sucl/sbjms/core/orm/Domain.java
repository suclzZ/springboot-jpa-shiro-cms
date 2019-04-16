package com.sucl.sbjms.core.orm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

/**
 *  id生成策略 org.hibernate.id.UUIDGenerator，uuid
 * 实体标记接口
 * @author sucl
 * @date 2019/4/1
 */
//@JsonIgnoreProperties({ "handler","hibernateLazyInitializer","fieldHandler"})
@GenericGenerator(name = "sys-uuid",strategy = "org.hibernate.id.UUIDGenerator")
public interface Domain extends Serializable {

}
