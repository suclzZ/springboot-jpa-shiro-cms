package com.sucl.sbjms.core.view;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/9
 */
public interface TreeNode {

    public String getName();

    public boolean isSpread();

    public String getHref();

    public List<TreeNode> getChildren();

    public String getId();
}
