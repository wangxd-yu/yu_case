package com.yu.querydsl.modules.dept.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangxd
 * @date 2022-02-19 0:04
 */
@ToString
@Getter
@Setter
public class DeptWithSetterDTO {
    private String name;
    private Integer sort;
}
