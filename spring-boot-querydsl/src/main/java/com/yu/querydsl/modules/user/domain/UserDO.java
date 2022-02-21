package com.yu.querydsl.modules.user.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author wangxd
 * @date 2022-01-26 22:21
 */
@Data
@Table(name = "t_user")
@Entity
public class UserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long deptId;

    private String username;

    private String name;

}
