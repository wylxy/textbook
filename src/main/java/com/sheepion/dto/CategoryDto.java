package com.sheepion.dto;

import com.sheepion.enums.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "重点人群分类")
public class CategoryDto {
    @ApiModelProperty(value = "分类id")
    private Integer id;
    @ApiModelProperty(value = "分类")
    private String category;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.category = category.getCategory();
    }
}
