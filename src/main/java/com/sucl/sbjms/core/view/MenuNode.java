package com.sucl.sbjms.core.view;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/9
 */
@Data
public class MenuNode implements TreeNode{

    private String id;
    private String text;
    private String cls;
    private String link;
    private boolean leaf;
    private List<TreeNode> children;

    public void add(MenuNode menuNode) {
        if(children==null){
            this.children = new ArrayList<>();
        }
        this.children.add(menuNode);
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
    public List<TreeNode> getChildren() {
        return children;
    }
}
