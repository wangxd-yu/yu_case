package com.yu.querydsl.modules.dept.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.querydsl.modules.dept.domain.DeptDO;
import com.yu.querydsl.modules.dept.domain.QDeptDO;
import com.yu.querydsl.modules.dept.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxd
 * @date 2022-02-11 12:15
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Resource
    private DeptRepository deptRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @GetMapping
    public ResponseEntity<Object> get() {
        return new ResponseEntity<>(deptRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("m2")
    public ResponseEntity<Object> getM2() {
        QDeptDO qDeptDO = QDeptDO.deptDO;
        List<DeptDO> deptDOList = jpaQueryFactory.selectFrom(qDeptDO).fetch();
        return new ResponseEntity<>(deptDOList, HttpStatus.OK);
    }
}
