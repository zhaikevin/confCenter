package com.kevin.confcenter.admin.service.operation.impl;

import com.kevin.confcenter.admin.dao.operation.MenuDao;
import com.kevin.confcenter.admin.service.operation.MenuService;
import com.kevin.confcenter.common.bean.po.operation.Menu;
import com.kevin.confcenter.common.bean.vo.web.operation.MenuParentNode;
import com.kevin.confcenter.common.bean.vo.web.operation.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/7/3 15:13
 */
@Service(value = "menuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuParentNode> getNodeList() {
        return menuDao.getNodeList();
    }

    @Override
    public List<MenuParentNode> getParentNodes() {
        return menuDao.getParentNodes();
    }

    @Override
    public List<MenuVO> getMenuList() {
        return menuDao.getMenuList();
    }

    @Override
    public Menu getById(Long id) {
        return menuDao.getById(id);
    }

    @Override
    public Long create(Menu menu) {
        menuDao.insert(menu);
        return menu.getId();
    }

    @Override
    public void modify(Menu menu) {
        menuDao.update(menu);
    }

    @Override
    public void delete(Long id) {
        menuDao.delete(id);
    }
}
