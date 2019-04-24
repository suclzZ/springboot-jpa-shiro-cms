package com.sucl.sbjms.core.view;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/9
 */
@Data
public class TreeNode implements ITreeNode {

    private String id;
    private String pid;
    private String text;
    private String cls;
    private String link;
    private boolean leaf;
    private List<ITreeNode> children;
    private Object obj;//扩展属性

    public void add(TreeNode treeNode) {
        if(children==null){
            this.children = new ArrayList<>();
        }
        this.children.add(treeNode);
    }

    public String getLink() {
        return link==null?"javascript:;":link;
    }

    @Override
    public String getName() {
        return text;
    }

    @Override
    public boolean isSpread() {
        return !leaf;
    }

    @Override
    public String getHref() {
        return null;
    }

    @Override
    public List<ITreeNode> getChildren() {
        return children;
    }
}
