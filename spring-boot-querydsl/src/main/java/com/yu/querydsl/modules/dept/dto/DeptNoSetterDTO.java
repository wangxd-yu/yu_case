package com.yu.querydsl.modules.dept.dto;

import lombok.ToString;

/**
 * @author wangxd
 * @date 2022-02-19 0:04
 */
public class DeptNoSetterDTO {
    private String name;
    private Integer sort;

    @Override
    public String toString() {
        return "DeptNoSetterDTO{" +
                "name='" + name + '\'' +
                ", sort=" + sort +
                '}';
    }
}
