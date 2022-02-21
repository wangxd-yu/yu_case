package com.yu.querydsl.modules.dept.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author wangxd
 * @date 2022-01-26 22:21
 */
@Data
@Table(name = "t_dept")
@Entity
@ToString
public class DeptDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    public DeptDO() {
    }

    @QueryProjection
    public DeptDO(String name, Integer sort) {
        this.name = name;
        this.sort = sort;
    }

    @QueryProjection
    public DeptDO(Long id, String name, Integer sort) {
        this.id = id;
        this.name = name;
        this.sort = sort;
    }
}
