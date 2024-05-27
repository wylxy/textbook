package com.sheepion.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "重点人群分类")
public enum Category {
//    FIXME 去掉错误的分类
    //其他
    OTHER(0, "其他"),
    //贫穷人口
    POOR(1, "贫穷人口"),
    //残疾人
    DISABLED(2, "残疾人"),
    //孤寡老人
    LONELY(3, "孤寡老人"),
    //独居老人
    ALONE(4, "独居老人"),
    //失业人员
    UNEMPLOYED(5, "失业人员"),
    //低保人员
    LOW_INSURANCE(6, "低保人员"),
    ;
    private final Integer id;
    private final String category;
    public static Category getCategoryById(Integer id) {
        for (Category category : Category.values()) {
            if (category.id.equals(id)) {
                return category;
            }
        }
        return null;
    }
    public static String getCategoryNameById(Integer id) {
        for (Category category : Category.values()) {
            if (category.id.equals(id)) {
                return category.category;
            }
        }
        return null;
    }

    Category(Integer id, String category) {
        this.id = id;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
