package com.yu.querydsl.modules.user.repository;

import com.yu.querydsl.modules.user.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author wangxd
 * @date 2022-01-26 22:30
 */
public interface UserRepository extends JpaRepository<UserDO, Long>, QuerydslPredicateExecutor<UserDO> {
}
