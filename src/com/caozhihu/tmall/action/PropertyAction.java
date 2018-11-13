package com.caozhihu.tmall.action;

import com.caozhihu.tmall.util.Page;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.transaction.annotation.Transactional;

public class PropertyAction extends Action4Result {


    //根据传进来的category.id查询对应的property列表
    @Action("admin_property_list")
    public String list() {
        if (page == null) {
            page = new Page();
        }

        int total = propertyService.total(category); //
        page.setTotal(total);
        page.setParam("&category.id=" + category.getId());
        properties = propertyService.list(page, category);
        t2p(category);//作用是从数据库取出对应的category对象，并设置在Action的category属性上
        return "listProperty";
    }

    @Action("admin_property_add")
    @Transactional(readOnly = false)
    public String add(){
        propertyService.save(property);
        return "listPropertyPage";
    }

    @Action("admin_property_delete")
    public String delete() {
        //这里有一定的欠缺：当分类的属性被删除的时候，对应的属性值，应该怎么处理？
        // 给出提示，让用户先删除属性值，或者一个提示确认按钮，初步计划使用ajax实现，待实现
        t2p(property);
        propertyService.delete(property);
        return "listPropertyPage";
    }

    @Action("admin_property_edit")
    public String edit(){
        t2p(property);
        return "editProperty";
    }

    @Action("admin_property_update")
    public String update(){
        propertyService.update(property);//这里直接使用数据库操作更新，无需从数据库取对象并给Action赋值，所以不使用t2p();
        return "listPropertyPage";
    }
}
