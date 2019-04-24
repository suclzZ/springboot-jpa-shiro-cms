package com.sucl.sbjms.core.view;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/9
 */
public interface ITreeNode {

    String getName();

    boolean isSpread();

    String getHref();

    List<ITreeNode> getChildren();

    String getId();

    String getPid();

    Object getObj();
}
