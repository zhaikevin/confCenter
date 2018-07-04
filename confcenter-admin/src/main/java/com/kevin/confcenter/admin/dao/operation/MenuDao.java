package com.kevin.confcenter.admin.dao.operation;

import com.kevin.confcenter.common.bean.po.operation.Menu;
import com.kevin.confcenter.common.bean.vo.web.operation.MenuParentNode;
import com.kevin.confcenter.common.bean.vo.web.operation.MenuVO;

import java.util.List;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/7/3 14:11
 */
public interface MenuDao {

    /**
     * 获取列表
     *
     * @return List<MenuParentNode>
     */
    List<MenuParentNode> getNodeList();

    /**
     * 获取父结点
     *
     * @return
     */
    List<MenuParentNode> getParentNodes();

    /**
     * 获取菜单列表
     *
     * @return
     */
    List<MenuVO> getMenuList();

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
    void insert(Menu menu);

    /**
     * update
     *
     * @param menu
     */
    void update(Menu menu);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);
}
