package com.kevin.confcenter.admin.controller.operation;

import com.kevin.confcenter.admin.service.operation.MenuService;
import com.kevin.confcenter.common.bean.po.operation.Menu;
import com.kevin.confcenter.common.bean.vo.ResultInfo;
import com.kevin.confcenter.common.bean.vo.web.operation.MenuParentNode;
import com.kevin.confcenter.common.bean.vo.web.operation.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/7/3 11:43
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * Tree node列表
     *
     * @return
     */
    @RequestMapping(value = "/nodeList", method = RequestMethod.GET)
    public ResultInfo getNodeList() {
        List<MenuParentNode> list = menuService.getNodeList();
        return ResultInfo.success(list);
    }

    /**
     * 获取父节点
     *
     * @return
     */
    @RequestMapping(value = "/parentNodes", method = RequestMethod.GET)
    public ResultInfo getParentNodes() {
        List<MenuParentNode> list = menuService.getParentNodes();
        return ResultInfo.success(list);
    }

    /**
     * 菜单列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultInfo getMenuList() {
        List<MenuVO> list = menuService.getMenuList();
        return ResultInfo.success(list);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResultInfo detail(Long id) {
        return ResultInfo.success(menuService.getById(id));
    }

    /**
     * 新建
     *
     * @param menu
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultInfo create(Menu menu) {
        menuService.create(menu);
        return ResultInfo.success();
    }

    /**
     * 更新
     *
     * @param menu
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResultInfo modify(Menu menu) {
        menuService.modify(menu);
        return ResultInfo.success();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultInfo delete(Long id) {
        menuService.delete(id);
        return ResultInfo.success();
    }
}
