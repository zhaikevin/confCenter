package com.kevin.confcenter.admin.service.operation;

import com.kevin.confcenter.common.bean.po.operation.Menu;
import com.kevin.confcenter.common.bean.vo.web.operation.MenuParentNode;

import java.util.List;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/7/3 15:08
 */
public interface MenuService {

    /**
     * 获取列表
     *
     * @return List<MenuParentNode>
     */
    List<MenuParentNode> getMenuList();

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    Menu getById(Long id);

    /**
     * insert
     *
     * @param menu
     */
    Long create(Menu menu);

    /**
     * update
     *
     * @param menu
     */
    void modify(Menu menu);

    /**
     * 删除结点
     *
     * @param id
     */
    void delete(Long id);
}
