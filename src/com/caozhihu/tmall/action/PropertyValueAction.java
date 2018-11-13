package com.caozhihu.tmall.action;

import org.apache.struts2.convention.annotation.Action;

public class PropertyValueAction extends Action4Result {

    @Action("admin_propertyValue_edit")
    public String edit() {
        t2p(product);
        propertyValueService.init(product);
        propertyValues = propertyValueService.listByParent(product);
        return "editPropertyValue";
    }

    @Action("admin_propertyValue_update")
    public String update(){
        String value = propertyValue.getValue();//先获取从参数传过来的value，要在持久化之前进行，否则value会被抹掉
        t2p(propertyValue);//持久化，此方法中只根据id从数据库获取propertyValue对象，这时候propertyValue对象的value是之前存在数据库中的
        propertyValue.setValue(value);//为持久化的propertyValue对象设置网页传过来的value值
        propertyValueService.update(propertyValue);
        return "success.jsp";
    }
}
