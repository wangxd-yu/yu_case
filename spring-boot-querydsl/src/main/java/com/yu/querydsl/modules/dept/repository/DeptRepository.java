package com.yu.querydsl.modules.dept.repository;

import com.yu.querydsl.modules.dept.domain.DeptDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author wangxd
 * @date 2022-01-26 22:24
 */
public interface DeptRepository extends JpaRepository<DeptDO, Long>, QuerydslPredicateExecutor<DeptDO> {
}
