package com.sucl.sbjms.core.util;

import com.sucl.sbjms.core.view.ITreeNode;
import com.sucl.sbjms.core.view.TreeNode;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sucl
 * @date 2019/4/19
 */
public class TreeHelper<T> {

    /**
     *
     * @param types
     * @param parentCoder 获取子与父关联的值
     * @param menuNodeBuild 转换成treeNode
     * @return
     */
    public static <T> List<ITreeNode> buildMenuNode(List<T> types, Function<T,String> parentCoder, Function<T,TreeNode> menuNodeBuild){
        if(types!=null){
            return types.stream().filter(m -> {
                return StringUtils.isEmpty(parentCoder.apply(m));
            }).map(m->{
                TreeNode node = menuNodeBuild.apply(m);
                findChildren(node,types,parentCoder,menuNodeBuild);
                return node;
            }).collect(Collectors.toList());
        }
        return null;
    }

    private static <T> void findChildren(TreeNode menu, List<T> menus, Function<T, String> parentCoder, Function<T, TreeNode> menuNodeBuild) {
        menus.stream().forEach(m->{
            if(menu.getId().equals(parentCoder.apply(m))){
                TreeNode node = menuNodeBuild.apply(m);
                menu.add(node);
                findChildren(node,menus,parentCoder, menuNodeBuild);
            }
        });
    }

}
