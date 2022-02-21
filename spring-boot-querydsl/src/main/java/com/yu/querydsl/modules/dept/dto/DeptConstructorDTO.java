package com.yu.querydsl.modules.dept.dto;

import lombok.ToString;

/**
 * @author wangxd
 * @date 2022-02-19 0:04
 */
@ToString
public class DeptConstructorDTO {
    private String name;
    private Integer sort;

    public DeptConstructorDTO(String name, Integer sort) {
        this.name = name;
        this.sort = sort;
    }
}
