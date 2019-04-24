package com.sucl.sbjms.system.web;

import com.sucl.sbjms.core.view.TreeNode;
import com.sucl.sbjms.core.web.BaseController;
import com.sucl.sbjms.system.entity.Menu;
import com.sucl.sbjms.system.service.MenuService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController<MenuService,Menu> {

    @GetMapping("/tree")
    public List<TreeNode> menuTree(){
        List<Menu> menus = service.getMenusByUsername(null);
        return buildMenuNode(menus);
    }

    private List<TreeNode> buildMenuNode(List<Menu> menus){
        if(menus!=null){
            return menus.stream().filter(m -> {
               return StringUtils.isEmpty(m.getParentMenuCode());
            }).map(m->{
                TreeNode node = menuToNode(m);
                findChildren(node,menus);
                return node;
            }).collect(Collectors.toList());
        }
        return null;
    }

    private void findChildren(TreeNode menu, List<Menu> menus) {
        menus.stream().forEach(m->{
            if(menu.getId().equals(m.getParentMenuCode())){
                TreeNode node = menuToNode(m);
                menu.add(node);
                findChildren(node,menus);
            }
        });
    }

    private TreeNode menuToNode(Menu menu){
        TreeNode treeNode = new TreeNode();
        treeNode.setId(menu.getMenuCode());
        treeNode.setText(menu.getMenuName());
        treeNode.setCls(menu.getStyle());
        treeNode.setLink(menu.getPath());
        treeNode.setLeaf("1".equals(menu.getLeaf()));
        if("0".equals(menu.getLeaf())){
            treeNode.setChildren(new ArrayList<>());
        }
        return treeNode;
    }
}
