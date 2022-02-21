package com.yu.querydsl.modules.dept.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.ToString;

/**
 * @author wangxd
 * @date 2022-02-19 0:04
 */
@ToString
public class DeptConstructorUseAnnotationDTO {
    private String name;
    private Integer sort;

    @QueryProjection
    public DeptConstructorUseAnnotationDTO(String name, Integer sort) {
        this.name = name;
        this.sort = sort;
    }
}
