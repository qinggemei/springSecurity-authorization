package com.star.common;


import lombok.Data;

/**
 * Description : 分页基础表单
 */
@Data
//@ApiModel(value = "分页数据", description = "分页需要的表单数据")
public class PageForm<T extends PageForm<?>>{

    /**
     * 页码
     */
//    @ApiModelProperty(value = "页码 从第一页开始 1")
    private Integer current;

    /**
     * 每页显示的数量11
     */
//    @ApiModelProperty(value = "每页显示的数量 范围在1~100")
    private Integer size;

    /**
     * 计算当前页 ,方便mysql 进行分页查询
     * @return 返回 pageForm
     */
//    @ApiModelProperty(hidden = true)
    public T calcCurrent(){
        current = (current - 1 ) * size;
        return (T) this;
    }
}
